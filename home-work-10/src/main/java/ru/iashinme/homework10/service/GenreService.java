package ru.iashinme.homework10.service;

import ru.iashinme.homework10.dto.GenreDto;

import java.util.List;

public interface GenreService {

    GenreDto create(String name);

    GenreDto update(long id, String name);

    long counts();

    GenreDto findById(long id);

    List<GenreDto> findAll();

    void deleteById(long id);
}
