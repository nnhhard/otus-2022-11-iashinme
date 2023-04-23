CREATE schema homework;

CREATE TABLE HOMEWORK.AUTHORS
(
    ID         BIGSERIAL PRIMARY KEY,
    SURNAME    VARCHAR(255) NOT NULL,
    NAME       VARCHAR(255) NOT NULL,
    PATRONYMIC VARCHAR(255)
);

CREATE TABLE HOMEWORK.GENRES
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);

CREATE TABLE HOMEWORK.BOOKS
(
    ID        BIGSERIAL PRIMARY KEY,
    NAME      VARCHAR(255) NOT NULL,
    GENRE_ID  BIGINT       NOT NULL,
    AUTHOR_ID  BIGINT       NOT NULL,
    FOREIGN KEY (GENRE_ID) REFERENCES HOMEWORK.GENRES (ID),
    FOREIGN KEY (AUTHOR_ID) REFERENCES HOMEWORK.AUTHORS (ID)
);

CREATE TABLE HOMEWORK.COMMENTS
(
    ID              BIGSERIAL PRIMARY KEY,
    BOOK_ID         BIGINT references HOMEWORK.BOOKS(id) on delete cascade,
    MESSAGE_COMMENT VARCHAR(8000),
    TIME    TIMESTAMP DEFAULT current_timestamp
);

INSERT INTO HOMEWORK.AUTHORS (surname, name, patronymic)
VALUES ('Гоголь', 'Николай', 'Васильевич'),
       ('Толстой', 'Лев', 'Николаевич'),
       ('Достоевский', 'Фёдор', 'Михайлович'),
       ('Пушкин', 'Александр', 'Сергеевич'),
       ('Лермонтов', 'Михаил', 'Юрьевич');

INSERT INTO HOMEWORK.GENRES (name)
VALUES ('Проза'),
       ('Драма'),
       ('Стихотворение'),
       ('Поэма');

INSERT INTO HOMEWORK.BOOKS (name, genre_id, author_id)
VALUES ('Мёртвые души', (SELECT MAX(ID) FROM HOMEWORK.GENRES WHERE name = 'Поэма'), (SELECT MAX(ID) FROM HOMEWORK.AUTHORS WHERE surname = 'Гоголь' AND name = 'Николай' and patronymic = 'Васильевич')),
       ('Мёртвые души1', (SELECT MAX(ID) FROM HOMEWORK.GENRES WHERE name = 'Драма'), (SELECT MAX(ID) FROM HOMEWORK.AUTHORS WHERE surname = 'Лермонтов' AND name = 'Михаил' and patronymic = 'Юрьевич'));

INSERT INTO HOMEWORK.COMMENTS (book_id, message_comment)
VALUES ((SELECT MAX(ID) FROM HOMEWORK.BOOKS WHERE name = 'Мёртвые души'), 'На пару часов чтения');