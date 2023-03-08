package ru.iashinme.homework12.service;

import ru.iashinme.homework12.dto.BookDto;
import ru.iashinme.homework12.model.Book;

import java.util.List;

public interface BookService {

    long countBooks();

    BookDto save(Book book);

    BookDto findById(long id);

    List<BookDto> findAll();

    void deleteById(long id);
}
