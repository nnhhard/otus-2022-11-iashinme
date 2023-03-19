package ru.iashinme.homework14.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.iashinme.homework14.model.mongo.Book;

public interface BookMongoRepository extends MongoRepository<Book, String> {
}
