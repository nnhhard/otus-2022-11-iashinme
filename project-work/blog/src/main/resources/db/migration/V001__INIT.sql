drop table if exists blog.authorities;
CREATE TABLE blog.authorities
(
    id        BIGSERIAL primary key,
    authority VARCHAR(50) NOT NULL UNIQUE
);

drop table if exists blog.users;
CREATE TABLE blog.users
(
    id                      BIGSERIAL primary key,
    username                VARCHAR(50)  NOT NULL UNIQUE,
    password                VARCHAR(100) NOT NULL,
    full_name               VARCHAR(255),
    email                   VARCHAR(100),
    enabled                 BOOLEAN      NOT NULL DEFAULT true,
    account_non_expired     BOOLEAN      NOT NULL DEFAULT true,
    account_non_locked      BOOLEAN      NOT NULL DEFAULT true,
    credentials_non_expired BOOLEAN      NOT NULL DEFAULT true,
    authority_id            BIGINT references blog.authorities (id)
);

DROP TABLE IF EXISTS BLOG.TECHNOLOGIES;
CREATE TABLE BLOG.TECHNOLOGIES
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS BLOG.POSTS;
CREATE TABLE BLOG.POSTS
(
    ID            BIGSERIAL primary key,
    TITLE         varchar(255) not null,
    TEXT          text         not null,
    AUTHOR_ID     BIGINT       NOT NULL,
    TECHNOLOGY_ID BIGINT,
    IMAGE_ID      BIGINT,
    FOREIGN KEY (AUTHOR_ID) REFERENCES BLOG.USERS (ID),
    FOREIGN KEY (TECHNOLOGY_ID) REFERENCES BLOG.TECHNOLOGIES (ID)
);

DROP TABLE IF EXISTS BLOG.COMMENTS;
CREATE TABLE BLOG.COMMENTS
(
    ID        BIGSERIAL PRIMARY KEY,
    TEXT      TEXT                                               NOT NULL,
    AUTHOR_ID BIGINT                                             NOT NULL,
    POST_ID   BIGINT                                             NOT NULL,
    TIME      TIMESTAMP WITH TIME ZONE default current_timestamp NOT NULL,
    FOREIGN KEY (AUTHOR_ID) REFERENCES BLOG.USERS (ID),
    FOREIGN KEY (POST_ID) REFERENCES BLOG.POSTS (ID) ON DELETE CASCADE
);

INSERT INTO BLOG.TECHNOLOGIES (NAME)
VALUES ('python'),
       ('linux'),
       ('java'),
       ('kotlin'),
       ('react'),
       ('postgres'),
       ('spring'),
       ('raspberry'),
       ('vs_code'),
       ('nginx'),
       ('docker'),
       ('android'),
       ('c'),
       ('cpp'),
       ('js'),
       ('typescript'),
       ('other');


INSERT INTO blog.authorities (authority)
values ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_SECURITY');

INSERT INTO blog.users (username, password, full_name, email, authority_id)
values ('user', '$2a$08$BfFaDAgceB.XhR46EEMoHeB0Z/0jantzTIxBIxelRNNuZGgZuF0um', 'Ivanov Ivan', 'user@mail.com',
        (select id from blog.authorities where authority = 'ROLE_USER')),
       ('admin', '$2a$08$BfFaDAgceB.XhR46EEMoHeB0Z/0jantzTIxBIxelRNNuZGgZuF0um', 'Iashin Mickhail', 'admin@mail.com',
        (select id from blog.authorities where authority = 'ROLE_ADMIN')),
       ('security', '$2a$08$BfFaDAgceB.XhR46EEMoHeB0Z/0jantzTIxBIxelRNNuZGgZuF0um', 'Iashin Mikhail', 'user@mail.com',
        (select id from blog.authorities where authority = 'ROLE_SECURITY'));