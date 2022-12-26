package ru.iashinme.homework05.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.iashinme.homework05.dao.BookDao;
import ru.iashinme.homework05.domain.Author;
import ru.iashinme.homework05.domain.Book;
import ru.iashinme.homework05.domain.Genre;
import ru.iashinme.homework05.exception.ValidateException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public int countBooks() {
        return bookDao.count();
    }

    @Override
    public long createBook(String bookName, long bookAuthorId, long bookGenreId) {
        var book = getValidatedBook(0, bookName, bookAuthorId, bookGenreId);
        return bookDao.insert(book);
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public int deleteBookById(long id) {
        return bookDao.deleteById(id);
    }

    @Override
    public int updateBook(long id, String bookName, long bookAuthorId, long bookGenreId) {
        var book = getValidatedBook(id, bookName, bookAuthorId, bookGenreId);
        return bookDao.update(book);
    }

    private Book getValidatedBook(long id, String bookName, long bookAuthorId, long bookGenreId) {
        if(StringUtils.isBlank(bookName))
            throw new ValidateException("Book name is null or empty!");
        Author author;
        try {
            author = authorService.getAuthorById(bookAuthorId);
        } catch (Exception e) {
            throw new ValidateException("Author not found: " + e.getMessage(), e);
        }
        Genre genre;
        try {
            genre = genreService.getGenreById(bookGenreId);
        } catch (Exception e) {
            throw new ValidateException("Genre not found: " + e.getMessage(), e);
        }
        return new Book(id, bookName, author, genre);
    }
}
