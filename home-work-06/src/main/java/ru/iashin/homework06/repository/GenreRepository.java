package ru.iashin.homework06.repository;

import ru.iashin.homework06.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();

    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    void deleteById(long id);

    long count();
}
