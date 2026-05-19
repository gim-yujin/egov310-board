-- 테스트용 H2 스키마 (PostgreSQL 호환 모드)
DROP TABLE IF EXISTS tb_board;
DROP TABLE IF EXISTS tb_member;

CREATE TABLE tb_member (
    member_id     VARCHAR(50)  PRIMARY KEY,
    password      VARCHAR(100) NOT NULL,
    member_name   VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL,
    role          VARCHAR(20)  NOT NULL DEFAULT 'ROLE_USER',
    reg_dt        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_board (
    board_no      BIGINT       AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(200) NOT NULL,
    content       CLOB         NOT NULL,
    writer        VARCHAR(50)  NOT NULL,
    view_cnt      INTEGER      NOT NULL DEFAULT 0,
    reg_dt        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    upd_dt        TIMESTAMP
);

CREATE INDEX idx_board_reg_dt ON tb_board(reg_dt DESC);
