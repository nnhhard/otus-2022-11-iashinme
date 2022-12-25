package ru.iashinme.homework05.dao;

import ru.iashinme.homework05.domain.Author;

import java.util.List;

public interface AuthorDao {

    int count();

    long insert(Author author);

    int update(Author author);

    Author getById(long id);

    List<Author> getAll();

    int deleteById(long id);
}
