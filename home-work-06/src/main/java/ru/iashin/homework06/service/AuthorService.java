package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.AuthorDto;
import ru.iashin.homework06.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    long countAuthors();

    AuthorDto createAuthor(String authorSurname, String authorName, String authorPatronymic);

    AuthorDto updateAuthor(long id, String authorSurname, String authorName, String authorPatronymic);

    AuthorDto getAuthorById(long id);

    List<AuthorDto> getAllAuthors();

    void deleteAuthorById(long id);
}
