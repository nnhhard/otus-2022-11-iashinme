package ru.iashinme.homework09.service;

import ru.iashinme.homework09.dto.BookDto;
import ru.iashinme.homework09.model.Book;

import java.util.List;

public interface BookService {

    long countBooks();

    BookDto save(Book book);

    BookDto findById(long id);

    List<BookDto> findAll();

    void deleteById(long id);
}
