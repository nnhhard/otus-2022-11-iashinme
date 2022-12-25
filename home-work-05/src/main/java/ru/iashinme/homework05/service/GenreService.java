package ru.iashinme.homework05.service;

import ru.iashinme.homework05.domain.Genre;

import java.util.List;

public interface GenreService {

    long createGenre(String genreName);

    int updateGenre(long id, String genreName);

    int countGenres();

    Genre getGenreById(long id);

    List<Genre> getAllGenres();

    int deleteGenreById(long id);
}
