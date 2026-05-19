# Ubuntu 개발환경 셋업 — eGov 3.10 게시판

## 1. 필수 패키지 설치

```bash
sudo apt update
sudo apt install -y openjdk-8-jdk maven postgresql
java -version    # 1.8 확인
mvn -v
psql --version
```

## 2. PostgreSQL 사용자 / DB 생성

```bash
sudo -u postgres psql <<'SQL'
CREATE USER egov WITH PASSWORD 'egov';
CREATE DATABASE egovdb OWNER egov ENCODING 'UTF8';
GRANT ALL PRIVILEGES ON DATABASE egovdb TO egov;
SQL
```

비밀번호 접속이 안 되면 `pg_hba.conf` 의 `local`/`host` 행을 `md5` 로 변경하고 서비스 재시작:
```bash
sudo systemctl restart postgresql
```

## 3. 스키마 / 샘플 데이터 적재

```bash
psql -h localhost -U egov -d egovdb -f sql/01_schema_postgresql.sql
psql -h localhost -U egov -d egovdb -f sql/02_sample_data.sql
```

## 4. Tomcat 9 다운로드 (외부 실행용)

```bash
mkdir -p ~/apps && cd ~/apps
curl -sLO https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.89/bin/apache-tomcat-9.0.89.tar.gz
tar xzf apache-tomcat-9.0.89.tar.gz
mv apache-tomcat-9.0.89 tomcat9
```

## 5. 빌드 / 테스트

```bash
mvn clean test                       # 단위 테스트 (H2 인메모리)
mvn clean package -DskipTests        # WAR 생성: target/egov310-board.war
```

## 6. 배포

```bash
cp target/egov310-board.war ~/apps/tomcat9/webapps/
~/apps/tomcat9/bin/startup.sh
# http://localhost:8080/egov310-board/
```

로그: `~/apps/tomcat9/logs/catalina.out`

## 7. 샘플 계정

| ID    | PW         | 권한          |
|-------|------------|---------------|
| admin | password1  | ROLE_ADMIN    |
| user1 | password1  | ROLE_USER     |

(`sql/02_sample_data.sql` 의 BCrypt 해시 = `password1`)
