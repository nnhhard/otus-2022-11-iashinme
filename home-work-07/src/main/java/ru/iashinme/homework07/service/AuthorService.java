package ru.iashinme.homework07.service;

import ru.iashinme.homework07.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    long countAuthors();

    AuthorDto createAuthor(String authorSurname, String authorName, String authorPatronymic);

    AuthorDto updateAuthor(long id, String authorSurname, String authorName, String authorPatronymic);

    AuthorDto getAuthorById(long id);

    List<AuthorDto> getAllAuthors();

    void deleteAuthorById(long id);
}
