DROP TABLE member;

CREATE table member (
	id varchar(50) PRIMARY KEY,
	pw varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	email varchar(50) UNIQUE,
	auth int	-- 관리자: 1, 사용자: 3 등
);

SELECT id FROM member WHERE id = '';
SELECT COUNT(*) FROM member WHERE id = '';

SELECT * FROM member;