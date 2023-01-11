package ru.iashin.homework06.repository;

import ru.iashin.homework06.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();

    Author save(Author author);

    Optional<Author> findById(long id);

    void deleteById(long id);

    long count();
}
