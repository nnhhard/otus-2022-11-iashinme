package ru.iashinme.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.iashinme.homework08.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findByName(String name);
}
