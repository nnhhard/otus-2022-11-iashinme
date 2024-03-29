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