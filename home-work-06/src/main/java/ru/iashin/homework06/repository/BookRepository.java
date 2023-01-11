package ru.iashin.homework06.repository;

import ru.iashin.homework06.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Long count();

    List<Book> findAll();

    Optional<Book> findById(Long id);

    void deleteById(Long id);

    Book save(Book book);
}
