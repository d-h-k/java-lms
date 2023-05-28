-- H2 DB
DROP TABLE IF EXISTS course;
CREATE TABLE IF NOT EXISTS course
(
    course_id         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    title      varchar(255) not null,
    creator_id bigint       not null,
    created_at timestamp    not null,
    updated_at timestamp,
    primary key (course_id)
);

DROP TABLE IF EXISTS ns_user;
CREATE TABLE IF NOT EXISTS ns_user
(
    user_code    varchar(20) NOT NULL COMMENT 'ID',
    password   varchar(20) not null,
    name       varchar(20) not null,
    email      varchar(50),
    created_at timestamp not null default now(),
    updated_at timestamp,
    primary key (user_code)
);

DROP TABLE IF EXISTS question;
CREATE TABLE IF NOT EXISTS question
(
    question_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    writer_id  bigint,
    title      varchar(100) not null,
    contents   clob,
    deleted    boolean      not null,
    created_at timestamp not null default now(),
    updated_at timestamp,
    primary key (question_id)
);


DROP TABLE IF EXISTS answer;
CREATE TABLE IF NOT EXISTS answer
(
    answer_id          BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    contents    clob,
    deleted     boolean   not null,
    question_id bigint,
    writer_id   bigint,
    created_at  timestamp not null,
    updated_at  timestamp,
    primary key (answer_id)
);

DROP TABLE IF EXISTS delete_history;
CREATE TABLE IF NOT EXISTS delete_history
(
    id            BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    content_id    bigint,
    content_type  varchar(255),
    created_date  timestamp,
    deleted_by_id bigint,
    primary key (id)
);

DROP TABLE IF EXISTS image;
CREATE TABLE IF NOT EXISTS image
(
    image_id      BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    image_url      varchar(255),
    primary key (image_id)
);