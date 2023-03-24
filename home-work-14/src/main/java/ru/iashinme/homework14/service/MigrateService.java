package ru.iashinme.homework14.service;

import ru.iashinme.homework14.model.h2.AuthorSQL;
import ru.iashinme.homework14.model.h2.BookSQL;
import ru.iashinme.homework14.model.h2.GenreSQL;
import ru.iashinme.homework14.model.mongo.Author;
import ru.iashinme.homework14.model.mongo.Book;
import ru.iashinme.homework14.model.mongo.Genre;

public interface MigrateService {

    AuthorSQL authorConvert(Author author);
    GenreSQL genreConvert(Genre author);
    BookSQL bookConvert(Book book);
}
