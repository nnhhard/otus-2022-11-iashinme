drop table if exists fs.file_info;
CREATE TABLE fs.file_info
(
    id   BIGSERIAL primary key,
    name TEXT  NOT NULL,
    guid TEXT  NOT NULL UNIQUE,
    type TEXT  NOT NULL,
    data bytea NOT NULL

);