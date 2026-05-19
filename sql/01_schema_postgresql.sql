-- =============================================================
-- eGov 3.10 학습용 게시판 - PostgreSQL 스키마
-- =============================================================

DROP TABLE IF EXISTS tb_board;
DROP TABLE IF EXISTS tb_member;

-- 회원
CREATE TABLE tb_member (
    member_id     VARCHAR(50)  PRIMARY KEY,
    password      VARCHAR(100) NOT NULL,
    member_name   VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL,
    role          VARCHAR(20)  NOT NULL DEFAULT 'ROLE_USER',
    reg_dt        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 게시판
CREATE TABLE tb_board (
    board_no      BIGSERIAL    PRIMARY KEY,
    title         VARCHAR(200) NOT NULL,
    content       TEXT         NOT NULL,
    writer        VARCHAR(50)  NOT NULL REFERENCES tb_member(member_id),
    view_cnt      INTEGER      NOT NULL DEFAULT 0,
    reg_dt        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    upd_dt        TIMESTAMP
);

CREATE INDEX idx_board_reg_dt ON tb_board(reg_dt DESC);
