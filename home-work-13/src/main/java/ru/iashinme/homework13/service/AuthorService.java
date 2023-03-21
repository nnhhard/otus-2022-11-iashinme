package ru.iashinme.homework13.service;

import ru.iashinme.homework13.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    long countAuthors();

    AuthorDto create(String surname, String name, String patronymic);

    AuthorDto update(long id, String surname, String name, String patronymic);

    AuthorDto findById(long id);

    List<AuthorDto> findAll();

    void deleteById(long id);
}
