package ru.iashinme.homework17.service;

import ru.iashinme.homework17.dto.GenreDto;

import java.util.List;

public interface GenreService {

    GenreDto create(String name);

    GenreDto update(long id, String name);

    long counts();

    GenreDto findById(long id);

    List<GenreDto> findAll();

    void deleteById(long id);
}
