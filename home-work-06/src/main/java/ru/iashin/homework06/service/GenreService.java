package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreDto createGenre(String genreName);

    GenreDto updateGenre(long id, String genreName);

    long countGenres();

    GenreDto getGenreById(long id);

    List<GenreDto> getAllGenres();

    void deleteGenreById(long id);
}
