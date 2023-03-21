package ru.iashinme.homework13.service;

import ru.iashinme.homework13.dto.BookDto;
import ru.iashinme.homework13.model.Book;

import java.util.List;

public interface BookService {

    long countBooks();

    BookDto save(Book book);

    BookDto findById(long id);

    List<BookDto> findAll();

    void deleteById(long id);
}
