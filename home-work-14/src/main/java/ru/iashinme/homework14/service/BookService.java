package ru.iashinme.homework14.service;

import ru.iashinme.homework14.model.mongo.Book;
import ru.iashinme.homework14.model.h2.BookSQL;

public interface BookService {

    BookSQL convert(Book book);
}
