package ru.iashinme.homework14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.iashinme.homework14.model.mongo.Author;

import java.util.Optional;

public interface AuthorMongoRepository extends MongoRepository<Author, String> {
    Optional<Author> findByName(String name);
}
