INSERT INTO blog.users (username, password, full_name, email)
values ('security', '$2a$08$BfFaDAgceB.XhR46EEMoHeB0Z/0jantzTIxBIxelRNNuZGgZuF0um', 'Iashin Mikhail', 'user@mail.com');

INSERT INTO blog.authorities (user_id, authority)
values ((select id from blog.users where username = 'security'), 'ROLE_SECURITY');