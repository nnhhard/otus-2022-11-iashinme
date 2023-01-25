package ru.iashinme.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.iashinme.homework08.model.Author;
import ru.iashinme.homework08.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByName(String name);
    Long countAllByGenre_id(String id);
    Long countAllByAuthors(Author author);
}
