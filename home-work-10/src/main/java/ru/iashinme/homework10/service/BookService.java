package ru.iashinme.homework10.service;

import ru.iashinme.homework10.dto.BookDto;
import ru.iashinme.homework10.dto.BookSmallDto;
import ru.iashinme.homework10.model.Book;

import java.util.List;

public interface BookService {

    long countBooks();

    BookDto save(Book book);

    BookDto create(String name, long authorId, long genreId);

    BookDto update(long id, String name, long authorId, long genreId);

    BookDto findById(long id);

    List<BookDto> findAll();

    void deleteById(long id);
}
