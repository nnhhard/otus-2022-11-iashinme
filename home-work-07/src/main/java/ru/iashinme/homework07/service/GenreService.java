package ru.iashinme.homework07.service;

import ru.iashinme.homework07.dto.GenreDto;

import java.util.List;

public interface GenreService {

    GenreDto createGenre(String genreName);

    GenreDto updateGenre(long id, String genreName);

    long countGenres();

    GenreDto getGenreById(long id);

    List<GenreDto> getAllGenres();

    void deleteGenreById(long id);
}
