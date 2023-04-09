package ru.iashinme.homework17.service;

import ru.iashinme.homework17.dto.BookDto;
import ru.iashinme.homework17.dto.BookSmallDto;
import ru.iashinme.homework17.model.Book;

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
