package ru.iashinme.homework05.dao;

import ru.iashinme.homework05.domain.Book;

import java.util.List;

public interface BookDao {
    int count();

    long insert(Book book);

    Book getById(long id);

    List<Book> getAll();

    int deleteById(long id);

    int update(Book book);
}
