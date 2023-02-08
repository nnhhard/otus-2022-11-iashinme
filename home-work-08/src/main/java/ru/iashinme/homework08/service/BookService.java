package ru.iashinme.homework08.service;

import ru.iashinme.homework08.dto.BookWithAllInfoDto;
import ru.iashinme.homework08.dto.BookWithIdNameGenreDto;

import java.util.List;

public interface BookService {

    long countBooks();

    BookWithAllInfoDto addAuthorForBook(String id, String authorId);

    BookWithAllInfoDto deleteAuthorInBook(String id, String authorId);

    BookWithIdNameGenreDto createBook(String bookName, String genreId);

    BookWithAllInfoDto getBookById(String id);

    List<BookWithIdNameGenreDto> getAllBooks();

    void deleteBookById(String id);

    BookWithIdNameGenreDto updateBook(String id, String bookName, String genreId);
}
