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

INSERT INTO BOOKS (name, author_id, genre_id)
VALUES ('Мёртвые души',
        (SELECT MAX(ID) FROM AUTHORS WHERE surname = 'Гоголь' AND name = 'Николай' and patronymic = 'Васильевич'),
        (SELECT MAX(ID) FROM GENRES WHERE name = 'Поэма'));