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

INSERT INTO BOOKS (name, genre_id, author_id)
VALUES ('Мёртвые души', (SELECT MAX(ID) FROM GENRES WHERE name = 'Поэма'), (SELECT MAX(ID) FROM AUTHORS WHERE surname = 'Гоголь' AND name = 'Николай' and patronymic = 'Васильевич')),
       ('Мёртвые души1', (SELECT MAX(ID) FROM GENRES WHERE name = 'Драма'), (SELECT MAX(ID) FROM AUTHORS WHERE surname = 'Лермонтов' AND name = 'Михаил' and patronymic = 'Юрьевич'));

INSERT INTO COMMENTS (book_id, message_comment)
VALUES ((SELECT MAX(ID) FROM BOOKS WHERE name = 'Мёртвые души'), 'На пару часов чтения');

INSERT INTO users (username, password, full_name, email)
values ('admin', '$2a$10$zbtytweNc/j.QSXKnJXEz.ZdCBNUKej8sftcReKz0I9tkQ.lvr952', 'IME', 'admin@mail.com');

INSERT INTO authorities (user_id, authority)
values (select id from users where username = 'admin', 'ROLE_ADMIN');