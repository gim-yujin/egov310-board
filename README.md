# egov310-board

전자정부프레임워크(**eGovFramework**) **3.10** 학습용 게시판 프로젝트.

## 스택

- JDK 1.8 / Maven 3.x
- Spring 4.3.25, Spring Security 4.2.13, MyBatis 3.4.6 (mybatis-spring 1.3.2)
- View: JSP + JSTL + jQuery 3.6
- DB: PostgreSQL 13+ (운영) / H2 (테스트)
- WAS: Apache Tomcat 9

## 기능

- 회원가입 / 로그인 (Spring Security + BCrypt)
- 게시글 CRUD, 페이징, 검색, 조회수 증가
- 작성자 본인만 수정/삭제 가능

## 빠른 시작

```bash
# 1) DB 준비 (PostgreSQL)
sudo -u postgres psql -c "CREATE USER egov WITH PASSWORD 'egov';"
sudo -u postgres psql -c "CREATE DATABASE egovdb OWNER egov;"
psql -U egov -d egovdb -f sql/01_schema_postgresql.sql
psql -U egov -d egovdb -f sql/02_sample_data.sql

# 2) 빌드 & 테스트 (H2 인메모리)
mvn clean test

# 3) WAR 생성 & Tomcat 9 배포
mvn package -DskipTests
cp target/egov310-board.war $CATALINA_HOME/webapps/
$CATALINA_HOME/bin/startup.sh
# 접속: http://localhost:8080/egov310-board/
```

자세한 설치 절차는 [doc/setup-ubuntu.md](doc/setup-ubuntu.md) 참고.
ERD는 [doc/erd.md](doc/erd.md).

---

## WSL2 + VSCode + Ubuntu 에서 실행하기

다른 컴퓨터(Windows + WSL2 + Ubuntu + VSCode) 에서 처음부터 셋업하는 절차다.

### 0. Windows 측 사전 준비

```powershell
# PowerShell (관리자) — WSL2 + Ubuntu 22.04 LTS 설치
wsl --install -d Ubuntu-22.04
wsl --set-default-version 2
wsl -l -v          # VERSION 2 인지 확인
```

VSCode 확장:
- **WSL** (`ms-vscode-remote.remote-wsl`) — 필수
- Extension Pack for Java (`vscjava.vscode-java-pack`)
- Spring Boot Extension Pack (`vmware.vscode-boot-dev-pack`) — 선택
- PostgreSQL (`ckolkman.vscode-postgres`) — 선택

VSCode 좌하단 `><` 클릭 → **"Connect to WSL"** 로 Ubuntu 내부에서 작업하기.

### 1. Ubuntu 패키지 설치 (WSL2 안에서)

```bash
sudo apt update
sudo apt install -y openjdk-8-jdk maven postgresql curl git
java -version    # 1.8.x
mvn -v
```

`openjdk-8-jdk` 가 default-jdk 가 아닐 경우:
```bash
sudo update-alternatives --config java   # 1.8 선택
echo 'export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64' >> ~/.bashrc
source ~/.bashrc
```

### 2. 프로젝트 복제 — **반드시 WSL 파일시스템 안에** (`/mnt/c/...` 금지)

```bash
cd ~                     # 또는 /home/<user> 아래
git clone <repo-url> egov310-board
cd egov310-board
```

> ⚠️ `/mnt/c/` (Windows 드라이브) 아래에 두면 Maven/Tomcat I/O 가 10~20배 느려지고
>    파일 권한/줄바꿈 문제가 생긴다. 항상 WSL 네이티브 파일시스템(`~`) 에서 작업한다.

### 3. PostgreSQL 기동 (WSL2 는 systemd 가 기본 비활성)

```bash
sudo service postgresql start                 # systemd 미사용 시
# (또는 /etc/wsl.conf 에 systemd=true 후 wsl --shutdown 으로 재기동했다면 systemctl 사용 가능)

sudo -u postgres psql <<'SQL'
CREATE USER egov WITH PASSWORD 'egov';
CREATE DATABASE egovdb OWNER egov ENCODING 'UTF8';
GRANT ALL PRIVILEGES ON DATABASE egovdb TO egov;
SQL

PGPASSWORD=egov psql -h 127.0.0.1 -U egov -d egovdb -f sql/01_schema_postgresql.sql
PGPASSWORD=egov psql -h 127.0.0.1 -U egov -d egovdb -f sql/02_sample_data.sql
```

### 4. Tomcat 9 설치 (사용자 홈에 설치)

```bash
mkdir -p ~/apps && cd ~/apps
curl -sLO https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.89/bin/apache-tomcat-9.0.89.tar.gz
tar xzf apache-tomcat-9.0.89.tar.gz && mv apache-tomcat-9.0.89 tomcat9
rm apache-tomcat-9.0.89.tar.gz
echo 'export CATALINA_HOME=~/apps/tomcat9' >> ~/.bashrc
source ~/.bashrc
```

### 5. 빌드 → 배포 → 기동

```bash
cd ~/egov310-board
mvn clean package -DskipTests
cp target/egov310-board.war $CATALINA_HOME/webapps/
$CATALINA_HOME/bin/startup.sh
tail -f $CATALINA_HOME/logs/catalina.out      # 로그 확인 (Ctrl+C 로 빠져나옴)
```

### 6. Windows 브라우저에서 접속

```
http://localhost:8080/egov310-board/
```

WSL2 는 기본적으로 Windows ↔ WSL 포트 포워딩이 자동이라 `localhost:8080` 로 바로 접속된다.
**접속이 안 될 때 체크리스트**:
- WSL 내부에서 `ss -ltn | grep 8080` 으로 listening 확인
- Windows Defender 방화벽이 Tomcat을 차단하지 않는지
- `wsl --shutdown` 후 재기동 (포트 포워딩 상태 리셋)
- `curl http://localhost:8080/egov310-board/` 를 WSL 내부에서 먼저 시도

### 7. 자주 마주치는 WSL2 이슈

| 증상 | 원인 / 해결 |
|------|------------|
| `mvn` 이 매우 느림 | 프로젝트가 `/mnt/c/...` 에 있음 → `~/` 로 옮긴다 |
| `psql: connection refused` | `sudo service postgresql start` 누락 |
| `peer authentication failed` | `pg_hba.conf` 의 `local` 라인을 `md5` 로 변경 후 `sudo service postgresql restart` |
| `*.sh: /bin/bash^M: bad interpreter` | Git autocrlf 로 CRLF 가 들어옴 → `git config --global core.autocrlf input` |
| Tomcat 기동 후 `Cannot find -tools.jar` | `JAVA_HOME` 이 JRE 를 가리킴 → JDK 경로로 설정 |
| 매번 WSL 시작 시 DB 가 꺼져있음 | `~/.bashrc` 에 `sudo service postgresql start` 추가 또는 `/etc/wsl.conf` 에 `systemd=true` 활성화 |

### 8. VSCode 디버그 실행 (선택)

`.vscode/launch.json` 예시 (Tomcat 9 에 Remote Debug 8000 포트):

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Attach to Tomcat (8000)",
            "request": "attach",
            "hostName": "localhost",
            "port": 8000
        }
    ]
}
```

Tomcat 측에서는 디버그 포트 활성화:
```bash
$CATALINA_HOME/bin/catalina.sh jpda start
# JDWP 가 8000 에서 listening
```

---

## 디렉터리 구조

```
egov310-board/
├── pom.xml
├── doc/                       # 환경 셋업 / ERD 문서
├── sql/                       # PostgreSQL 스키마 + 샘플 데이터
└── src/
    ├── main/
    │   ├── java/egovframework/example/board/
    │   │   ├── cmm/           # 공통 (예외, 페이징)
    │   │   ├── interceptor/
    │   │   ├── member/        # 회원 (web/service/security)
    │   │   └── board/         # 게시판 (web/service)
    │   ├── resources/
    │   │   ├── egovframework/
    │   │   │   ├── spring/    # context-*.xml
    │   │   │   ├── springmvc/ # dispatcher-servlet.xml
    │   │   │   ├── mapper/    # MyBatis 매퍼
    │   │   │   └── message/   # 다국어 메시지
    │   │   ├── egovProps/     # globals.properties
    │   │   └── log4j2.xml
    │   └── webapp/
    │       ├── WEB-INF/
    │       │   ├── web.xml
    │       │   └── jsp/       # common / member / board
    │       └── resources/     # css, js (jQuery), img
    └── test/
        ├── java/...           # MemberServiceTest, BoardServiceTest
        └── resources/         # test-context.xml, schema-h2.sql
```

## 샘플 계정

| ID    | PW        | 권한        |
|-------|-----------|-------------|
| admin | password1 | ROLE_ADMIN  |
| user1 | password1 | ROLE_USER   |

## 학습 포인트

- eGov 표준 디렉터리/명명 컨벤션 (`egovframework/spring`, `egovProps/globals.properties`)
- XML 기반 Spring 설정 분리 (datasource / mapper / transaction / security)
- MyBatis `@Mapper` + XML mapper 조합
- Spring Security 4.2 XML 설정 + `UserDetailsService` 커스텀 구현
- PostgreSQL `BIGSERIAL` + MyBatis `useGeneratedKeys`
