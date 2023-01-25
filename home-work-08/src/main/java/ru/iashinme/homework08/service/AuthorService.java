package ru.iashinme.homework08.service;

import ru.iashinme.homework08.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    long countAuthors();

    AuthorDto createAuthor(String authorSurname, String authorName, String authorPatronymic);

    AuthorDto updateAuthor(String id, String authorSurname, String authorName, String authorPatronymic);

    AuthorDto getAuthorById(String id);

    List<AuthorDto> getAllAuthors();

    void deleteAuthorById(String id);
}
