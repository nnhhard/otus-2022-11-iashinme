package ru.iashinme.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.iashinme.homework08.model.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

    Optional<Genre> findByName(String name);
}
