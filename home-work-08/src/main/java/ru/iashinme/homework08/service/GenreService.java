package ru.iashinme.homework08.service;

import ru.iashinme.homework08.dto.GenreDto;

import java.util.List;

public interface GenreService {

    GenreDto createGenre(String genreName);

    GenreDto updateGenre(String id, String genreName);

    long countGenres();

    GenreDto getGenreById(String id);

    List<GenreDto> getAllGenres();

    void deleteGenreById(String id);
}
