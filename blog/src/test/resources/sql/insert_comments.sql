insert into blog.users (id, username, password, full_name, email)
values (-1, 'testUser', '123', 'Test', 'test@mail.com'),
        (-2, 'testUser2', '123', 'Test2', 'test2@mail.com');

insert into blog.technologies (id, name)
values (-1, 'javaee');

insert into blog.posts (id, title, text, author_id, technology_id)
values (-1, 'title', 'text', -1, -1);

insert into blog.comments (id, text, author_id, post_id, time)
values (-1, 'comment text', -2, -1, '2023-05-01T00:00'),
       (-2, 'comment text 2', -1, -1,'2023-05-02T00:00');

