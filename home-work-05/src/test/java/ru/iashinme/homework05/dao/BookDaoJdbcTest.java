package ru.iashinme.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.iashinme.homework05.domain.Author;
import ru.iashinme.homework05.domain.Book;
import ru.iashinme.homework05.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOKS_COUNT = 1;
    private static final int EXISTING_BOOK_ID = -1;
    private static final String EXISTING_BOOK_NAME = "Мёртвые души том №1";

    private static final int AUTHOR_ID = -1;
    private static final String AUTHOR_SURNAME = "Гоголь";
    private static final String AUTHOR_NAME = "Николай";
    private static final String AUTHOR_PATRONYMIC = "Васильевич";

    private static final int GENRE_ID = -1;
    private static final String GENRE_NAME = "Поэма";

    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        int actualBookCount = bookDao.count();
        assertThat(actualBookCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Author author = new Author(AUTHOR_ID, AUTHOR_SURNAME, AUTHOR_NAME, AUTHOR_PATRONYMIC);
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        Book expectedBook = new Book("Мертвые дущи том №2", author, genre);
        var id = bookDao.insert(expectedBook);
        expectedBook.setId(id);
        var actualBook = bookDao.getById(id);
        assertThat(actualBook).isEqualTo(expectedBook);
    }


    @DisplayName("возвращать ожидаемую книгу по его id")
    @Test
    void shouldReturnExpectedBookById() {
        Author author = new Author(AUTHOR_ID, AUTHOR_SURNAME, AUTHOR_NAME, AUTHOR_PATRONYMIC);
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, author, genre);
        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("удалять заданную кгнигу по его id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        var numberDeleteRow = bookDao.deleteById(EXISTING_BOOK_ID);
        assertThat(numberDeleteRow).isEqualTo(1);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        Author author = new Author(AUTHOR_ID, AUTHOR_SURNAME, AUTHOR_NAME, AUTHOR_PATRONYMIC);
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, author, genre);

        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList)
                .containsExactlyInAnyOrder(expectedBook);
    }

    @DisplayName("обновлять книгу по id")
    @Test
    void shouldUpdateBookById() {
        Author author = new Author(AUTHOR_ID, AUTHOR_SURNAME, AUTHOR_NAME, AUTHOR_PATRONYMIC);
        Genre genre = new Genre(GENRE_ID, GENRE_NAME);
        Book expectedBook = new Book(EXISTING_BOOK_ID, "Мертвые душы том №3", author, genre);
        var numberUpdateRow = bookDao.update(expectedBook);
        assertThat(numberUpdateRow).isEqualTo(1);
        var actualBook = bookDao.getById(expectedBook.getId());

        assertThat(actualBook)
                .isEqualTo(expectedBook);
    }
}