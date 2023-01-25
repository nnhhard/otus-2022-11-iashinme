package ru.iashinme.homework08.service;

import ru.iashinme.homework08.dto.BookWithAllInfoDto;
import ru.iashinme.homework08.dto.BookWithIdNameGenreDto;
import ru.iashinme.homework08.model.Author;

import java.util.List;

public interface BookService {

    long countBooks();

    long countBooksByGenreId(String genreId);
    
    long countBooksByAuthor(Author author);

    BookWithAllInfoDto addAuthorForBook(String id, String authorId);

    BookWithAllInfoDto deleteAuthorInBook(String id, String authorId);

    BookWithIdNameGenreDto createBook(String bookName, String bookGenreId);

    BookWithAllInfoDto getBookById(String id);

    List<BookWithIdNameGenreDto> getAllBooks();

    void deleteBookById(String id);

    BookWithIdNameGenreDto updateBook(String id, String bookName, String bookGenreId);
}
