package ru.iashinme.homework07.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework07.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "book-genre-graph")
    List<Book> findAll();

    @EntityGraph(value = "book-genre-graph")
    Optional<Book> findById(long id);
}
