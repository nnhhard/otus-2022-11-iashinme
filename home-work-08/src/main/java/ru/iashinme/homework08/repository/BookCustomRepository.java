package ru.iashinme.homework08.repository;

import ru.iashinme.homework08.model.Genre;

public interface BookCustomRepository {

    long updateGenreInBook(Genre genre);
}
