INSERT INTO AUTHORS (surname, name, patronymic)
VALUES ('Гоголь', 'Николай', 'Васильевич'),
       ('Толстой', 'Лев', 'Николаевич'),
       ('Достоевский', 'Фёдор', 'Михайлович'),
       ('Пушкин', 'Александр', 'Сергеевич'),
       ('Лермонтов', 'Михаил', 'Юрьевич');

INSERT INTO GENRES (name)
VALUES ('Проза'),
       ('Драма'),
       ('Стихотворение'),
       ('Поэма');

INSERT INTO BOOKS (name, genre_id)
VALUES ('Мёртвые души', (SELECT MAX(ID) FROM GENRES WHERE name = 'Поэма')),
       ('Мёртвые души1', (SELECT MAX(ID) FROM GENRES WHERE name = 'Драма'));

INSERT INTO COMMENTS (book_id, message_comment)
VALUES ((SELECT MAX(ID) FROM BOOKS WHERE name = 'Мёртвые души'), 'На пару часов чтения');

INSERT INTO BOOKS_AUTHORS (book_id, author_id)
VALUES ((SELECT MAX(ID) FROM BOOKS WHERE name = 'Мёртвые души'),
        (SELECT MAX(ID) FROM AUTHORS WHERE surname = 'Гоголь' AND name = 'Николай' and patronymic = 'Васильевич'));

INSERT INTO BOOKS_AUTHORS (book_id, author_id)
VALUES ((SELECT MAX(ID) FROM BOOKS WHERE name = 'Мёртвые души1'),
        (SELECT MAX(ID) FROM AUTHORS WHERE surname = 'Лермонтов' AND name = 'Михаил' and patronymic = 'Юрьевич'));