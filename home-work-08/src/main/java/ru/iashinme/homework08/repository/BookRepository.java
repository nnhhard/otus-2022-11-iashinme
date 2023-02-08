package ru.iashinme.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.iashinme.homework08.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomRepository {

    List<Book> findByName(String name);
    List<Book> findAllByAuthors_Id(String id);
    List<Book> findAllByGenre_Id(String id);
    boolean existsBookByAuthors_Id(String authorId);
    boolean existsBookByGenre_Id(String genreId);
}
