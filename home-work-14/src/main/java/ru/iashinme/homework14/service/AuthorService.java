package ru.iashinme.homework14.service;

import ru.iashinme.homework14.model.mongo.Author;
import ru.iashinme.homework14.model.h2.AuthorSQL;

public interface AuthorService {

    AuthorSQL convert(Author author);

    AuthorSQL findByMongoId(String mongoId);
}
