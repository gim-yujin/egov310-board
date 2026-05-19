-- =============================================================
-- 샘플 회원/게시글 (BCrypt 해시: 비밀번호는 'password1' )
-- =============================================================

INSERT INTO tb_member(member_id, password, member_name, email, role) VALUES
('admin', '$2a$10$4JeGqirMJ/THap9tyX6wsOeoFHzcqsD0DYnQSXSgsKkpZnYmE0tpW', '관리자', 'admin@example.com', 'ROLE_ADMIN'),
('user1', '$2a$10$4JeGqirMJ/THap9tyX6wsOeoFHzcqsD0DYnQSXSgsKkpZnYmE0tpW', '홍길동', 'user1@example.com', 'ROLE_USER');

INSERT INTO tb_board(title, content, writer) VALUES
('첫 번째 게시글', '안녕하세요, eGov 3.10 학습용 게시판입니다.', 'admin'),
('두 번째 게시글', 'jQuery + JSP + MyBatis 조합 테스트.', 'user1'),
('세 번째 게시글', 'PostgreSQL과 Spring Security 연동 확인.', 'user1');
