package ru.iashinme.homework05.dao;

import ru.iashinme.homework05.domain.Genre;

import java.util.List;

public interface GenreDao {

    int count();

    long insert(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    int deleteById(long id);

    int update(Genre genre);
}
