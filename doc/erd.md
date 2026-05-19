# ERD

```
┌─────────────────────────────┐          ┌─────────────────────────────┐
│         tb_member           │          │         tb_board            │
├─────────────────────────────┤          ├─────────────────────────────┤
│ PK member_id    VARCHAR(50) │◄────────┐│ PK board_no   BIGSERIAL     │
│    password     VARCHAR(100)│         ││    title      VARCHAR(200)  │
│    member_name  VARCHAR(50) │         ││    content    TEXT          │
│    email        VARCHAR(100)│         └┤ FK writer     VARCHAR(50)   │
│    role         VARCHAR(20) │          │    view_cnt   INTEGER       │
│    reg_dt       TIMESTAMP   │          │    reg_dt     TIMESTAMP     │
└─────────────────────────────┘          │    upd_dt     TIMESTAMP     │
                                         └─────────────────────────────┘
```

## 키 / 인덱스

- `tb_member.member_id` : PK
- `tb_board.board_no`   : PK (`BIGSERIAL`)
- `tb_board.writer`     : `tb_member(member_id)` 참조
- `idx_board_reg_dt`    : `tb_board(reg_dt DESC)` 정렬 인덱스

## 비밀번호 저장

`tb_member.password` 는 **BCrypt 해시** (`$2a$10$...`)로 저장된다.
Spring Security 4.x의 `BCryptPasswordEncoder` 가 인코딩/검증을 담당한다.
