USE `jpa-study`;  -- 현재 사용 중인 데이터베이스 설정

CREATE TABLE MEMBER (
    ID VARCHAR(255) NOT NULL,  -- 아이디(기본 키)
    NAME VARCHAR(255),         -- 이름
    AGE INTEGER NOT NULL,      -- 나이
    PRIMARY KEY (ID)
);
