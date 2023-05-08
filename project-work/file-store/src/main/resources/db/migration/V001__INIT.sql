drop table if exists fs.file;
CREATE TABLE fs.file
(
    id   BIGSERIAL primary key,
    name TEXT  NOT NULL,
    type TEXT  NOT NULL,
    data bytea NOT NULL
);

drop table if exists fs.file_info;
CREATE TABLE fs.file_info
(
    id       BIGSERIAL primary key,
    name     TEXT NOT NULL,
    type     TEXT NOT NULL,
    file_path TEXT NOT NULL UNIQUE
);