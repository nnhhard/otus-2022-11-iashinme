package ru.iashinme.homework05.service;

import ru.iashinme.homework05.domain.Book;

import java.util.List;

public interface BookService {
    int countBooks();

    long createBook(String bookName, long bookAuthorId, long bookGenreId);

    Book getBookById(long id);

    List<Book> getAllBooks();

    int deleteBookById(long id);

    int updateBook(long id, String bookName, long bookAuthorId, long bookGenreId);
}
