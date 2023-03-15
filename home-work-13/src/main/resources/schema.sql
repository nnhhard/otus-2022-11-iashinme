DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS
(
    ID         BIGSERIAL PRIMARY KEY,
    SURNAME    VARCHAR(255) NOT NULL,
    NAME       VARCHAR(255) NOT NULL,
    PATRONYMIC VARCHAR(255)
);

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS
(
    ID        BIGSERIAL PRIMARY KEY,
    NAME      VARCHAR(255) NOT NULL,
    GENRE_ID  BIGINT       NOT NULL,
    AUTHOR_ID  BIGINT       NOT NULL,
    FOREIGN KEY (GENRE_ID) REFERENCES GENRES (ID),
    FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS (ID)
);

DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS
(
    ID              BIGSERIAL PRIMARY KEY,
    BOOK_ID         BIGINT references books(id) on delete cascade,
    MESSAGE_COMMENT VARCHAR(8000),
    TIME    DATETIME DEFAULT current_timestamp
);

DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
    id                      BIGSERIAL primary key,
    username                VARCHAR(50)  NOT NULL,
    password                VARCHAR(100) NOT NULL,
    full_name               VARCHAR(100),
    email                   VARCHAR(100),
    enabled                 boolean      NOT NULL DEFAULT true,
    account_non_expired     boolean      NOT NULL DEFAULT true,
    account_non_locked      boolean      NOT NULL DEFAULT true,
    credentials_non_expired boolean      NOT NULL DEFAULT true
);

DROP TABLE IF EXISTS authorities;
CREATE TABLE authorities
(
    id        BIGSERIAL primary key,
    user_id   bigint      NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE UNIQUE INDEX ix_auth_user
    on authorities (user_id, authority);