package ru.iashinme.homework14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.iashinme.homework14.model.mongo.Genre;

import java.util.Optional;

public interface GenreMongoRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByName(String name);
}
