package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.BookWithAllInfoDto;

import java.util.List;

public interface BookService {
    long countBooks();

    BookWithAllInfoDto addAuthorForBook(long id, long authorId);

    BookWithAllInfoDto deleteAuthorInBook(long id, long authorId);

    BookWithAllInfoDto createBook(String bookName, long bookGenreId);

    BookWithAllInfoDto getBookById(long id);

    List<BookWithAllInfoDto> getAllBooks();

    void deleteBookById(long id);

    BookWithAllInfoDto updateBook(long id, String bookName, long bookGenreId);
}
