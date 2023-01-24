package ru.iashinme.homework07.service;

import ru.iashinme.homework07.dto.BookWithAllInfoDto;
import ru.iashinme.homework07.dto.BookWithIdNameGenreDto;

import java.util.List;

public interface BookService {

    long countBooks();

    BookWithAllInfoDto addAuthorForBook(long id, long authorId);

    BookWithAllInfoDto deleteAuthorInBook(long id, long authorId);

    BookWithIdNameGenreDto createBook(String bookName, long bookGenreId);

    BookWithAllInfoDto getBookById(long id);

    List<BookWithIdNameGenreDto> getAllBooks();

    void deleteBookById(long id);

    BookWithIdNameGenreDto updateBook(long id, String bookName, long bookGenreId);
}
