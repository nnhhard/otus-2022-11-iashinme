INSERT INTO GENRES (id, name)
VALUES (-1, 'Поэма'),
       (-2, 'Стихи'),
       (-3, 'Детектив');

INSERT INTO AUTHORS (id, surname, name, patronymic)
VALUES (-1, 'Гоголь', 'Николай', 'Васильевич'),
       (-2, 'Толстой', 'Лев', 'Николаевич');


INSERT INTO BOOKS (id, name, genre_id, author_id)
VALUES (-1, 'Мёртвые души', -1, -1),
       (-2, 'Сборник стихов', -2, -1);



INSERT INTO COMMENTS(id, book_id, message_comment)
VALUES (-1, -1, 'На пару часов чтения'),
       (-2, -1, 'Спойлер - души умерли'),
       (-3, -2, 'На новый год буду рассказывать');

