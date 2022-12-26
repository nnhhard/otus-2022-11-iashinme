package ru.iashinme.homework05.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import ru.iashinme.homework05.dao.BookDao;
import ru.iashinme.homework05.domain.Author;
import ru.iashinme.homework05.domain.Book;
import ru.iashinme.homework05.domain.Genre;
import ru.iashinme.homework05.exception.ValidateException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами должен ")
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    private static final int EXPECTED_BOOK_COUNT = 1;

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки наименования книги")
    public void shouldHaveCorrectExceptionForBookName() {
        long id = -1L;
        String name = "";
        long authorId = -1L;
        long genreId = -1L;

        when(bookDao.insert(any(Book.class))).thenReturn(id);

        assertThatThrownBy(() -> bookService.createBook(name, authorId, genreId))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Book name is null or empty!");
    }

    @Test
    @DisplayName("корректны выбрасывать исключение при проверки существования автора")
    public void shouldHaveCorrectExceptionForBookExistAuthor() {
        long id = -1L;
        String name = "bookName";
        long authorId = -1L;
        long genreId = -1L;

        when(bookDao.insert(any(Book.class))).thenReturn(id);
        when(authorService.getAuthorById(authorId)).thenThrow(new DataAccessException("Error") {});

        assertThatThrownBy(() -> bookService.createBook(name, authorId, genreId))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Author not found: Error");
    }

    @Test
    @DisplayName("корректны выбрасывать исключение при проверки существования жанра")
    public void shouldHaveCorrectExceptionForBookExistGenre() {
        long id = -1L;
        String name = "bookName";
        long authorId = -1L;
        long genreId = -1L;

        when(bookDao.insert(any(Book.class))).thenReturn(id);
        when(genreService.getGenreById(authorId)).thenThrow(new DataAccessException("Error") {});

        assertThatThrownBy(() -> bookService.createBook(name, authorId, genreId))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Genre not found: Error");
    }

    @DisplayName("возвращать ожидаемое количество книг")
    @Test
    void shouldReturnExpectedBookCount() {
        when(bookDao.count()).thenReturn(EXPECTED_BOOK_COUNT);

        int actualBookCount = bookService.countBooks();
        assertThat(actualBookCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        Author author = new Author(-1, "AUTHOR_SURNAME", "AUTHOR_NAME", "AUTHOR_PATRONYMIC");
        Genre genre = new Genre(-1, "GENRE_NAME");
        Book expectedBook = new Book(-1, "Мертвые дущи том №2", author, genre);

        when(bookDao.getAll()).thenReturn(List.of(expectedBook));
        List<Book> actualList = bookService.getAllBooks();

        assertThat(actualList).usingRecursiveFieldByFieldElementComparator().containsExactly(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по его id")
    @Test
    void shouldReturnExpectedBookById() {
        Author author = new Author(-1, "AUTHOR_SURNAME", "AUTHOR_NAME", "AUTHOR_PATRONYMIC");
        Genre genre = new Genre(-1, "GENRE_NAME");
        Book expectedBook = new Book(-1, "Мертвые дущи том №2", author, genre);

        when(bookDao.getById(expectedBook.getId())).thenReturn(expectedBook);

        Book actualBook = bookService.getBookById(expectedBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }


    @DisplayName("добавлять книгу")
    @Test
    void shouldCreateBook() {
        long id = -1L;
        String name = "Мертвые дущи том №2";
        long authorId = -1L;
        long genreId = -1L;

        when(bookDao.insert(any(Book.class))).thenReturn(id);

        Long actualId = bookService.createBook(name, authorId, genreId);

        assertThat(actualId).isEqualTo(id);
    }

    @DisplayName("обновлять книгу по id")
    @Test
    void shouldUpdateBook() {
        long id = -1L;
        String name = "Мертвые дущи том №3";
        long authorId = -1L;
        long genreId = -1L;

        int expectedUpdatedCount = 1;

        when(bookDao.update(any(Book.class))).thenReturn(expectedUpdatedCount);
        int actualUpdatedCount = bookService.updateBook(id, name, authorId, genreId);

        assertThat(actualUpdatedCount).isEqualTo(expectedUpdatedCount);
    }

    @Test
    @DisplayName("удалять заданную книгу по её id")
    void shouldCorrectlyDeleteAuthor() {
        long id = -1L;
        int expectedDeletedCount = 1;

        when(bookDao.deleteById(id)).thenReturn(expectedDeletedCount);
        int actualDeletedCount = bookService.deleteBookById(id);

        assertThat(actualDeletedCount).isEqualTo(expectedDeletedCount);
    }


}
