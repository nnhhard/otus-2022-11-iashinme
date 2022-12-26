package ru.iashinme.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.iashinme.homework05.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final int EXPECTED_AUTHORS_COUNT = 1;
    private static final int EXISTING_AUTHOR_ID = -1;
    private static final String EXISTING_AUTHOR_SURNAME = "Гоголь";
    private static final String EXISTING_AUTHOR_NAME = "Николай";
    private static final String EXISTING_AUTHOR_PATRONYMIC = "Васильевич";

    @Autowired
    private AuthorDaoJdbc authorDao;

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        int actualAuthorCount = authorDao.count();
        assertThat(actualAuthorCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author("Лермонтов", "Михаил", "Юрьевич");
        var id = authorDao.insert(expectedAuthor);
        expectedAuthor.setId(id);
        var actualAuthor = authorDao.getById(id);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(
                EXISTING_AUTHOR_ID,
                EXISTING_AUTHOR_SURNAME,
                EXISTING_AUTHOR_NAME,
                EXISTING_AUTHOR_PATRONYMIC);
        Author actualAuthor = authorDao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }


    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        Author author = new Author("surname", "name", "patron");
        var id = authorDao.insert(author);

        assertThatCode(() -> authorDao.getById(id))
                .doesNotThrowAnyException();

        var numberDeleteRow = authorDao.deleteById(id);
        assertThat(numberDeleteRow).isEqualTo(1);

        assertThatThrownBy(() -> authorDao.getById(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        Author expectedFirstAuthor = new Author(
                EXISTING_AUTHOR_ID,
                EXISTING_AUTHOR_SURNAME,
                EXISTING_AUTHOR_NAME,
                EXISTING_AUTHOR_PATRONYMIC);
        List<Author> actualAuthorsList = authorDao.getAll();
        assertThat(actualAuthorsList)
                .hasSize(EXPECTED_AUTHORS_COUNT)
                .containsExactlyInAnyOrder(expectedFirstAuthor);
    }

    @DisplayName("обновлять автора по id")
    @Test
    void shouldUpdateAuthorById() {
        Author expectedAuthor = new Author(
                EXISTING_AUTHOR_ID,
                "new surname",
                "new name",
                "new patron");
        var numberUpdateRow = authorDao.update(expectedAuthor);
        assertThat(numberUpdateRow).isEqualTo(1);
        var actualAuthor = authorDao.getById(expectedAuthor.getId());
        assertThat(actualAuthor)
                .isEqualTo(expectedAuthor);
    }
}