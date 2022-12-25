package ru.iashinme.homework05.service;

import ru.iashinme.homework05.domain.Author;

import java.util.List;

public interface AuthorService {
    int countAuthors();

    long createAuthor(String authorSurname, String authorName, String authorPatronymic);

    int updateAuthor(long id, String authorSurname, String authorName, String authorPatronymic);

    Author getAuthorById(long id);

    List<Author> getAllAuthors();

    int deleteAuthorById(long id);
}
