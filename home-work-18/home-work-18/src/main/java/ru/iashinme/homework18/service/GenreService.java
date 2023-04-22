package ru.iashinme.homework18.service;

import ru.iashinme.homework18.dto.GenreDto;

import java.util.List;

public interface GenreService {

    GenreDto create(String name);

    GenreDto update(long id, String name);

    long counts();

    GenreDto findById(long id);

    List<GenreDto> findAll();

    void deleteById(long id);
}
