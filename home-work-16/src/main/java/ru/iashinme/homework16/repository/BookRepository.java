package ru.iashinme.homework16.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework16.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "book-genre-author-graph")
    List<Book> findAll();

    @EntityGraph(value = "book-genre-author-graph")
    Optional<Book> findById(long id);

    boolean existsBy();
}
