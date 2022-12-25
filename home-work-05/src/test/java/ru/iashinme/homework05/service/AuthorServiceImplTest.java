package ru.iashinme.homework05.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework05.dao.AuthorDao;
import ru.iashinme.homework05.domain.Author;
import ru.iashinme.homework05.exception.ValidateException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с авторами должен ")
@SpringBootTest(classes = AuthorServiceImpl.class)
public class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorDao authorDao;

    private static final int EXPECTED_AUTHOR_COUNT = 1;

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки фамилии автора")
    public void shouldHaveCorrectExceptionForAuthorSurname() {
        long id = -1L;
        String surname = "";
        String name = "name";
        String patronymic = "patronymic";


        when(authorDao.insert(any(Author.class))).thenReturn(id);

        assertThatThrownBy(() -> authorService.createAuthor(surname, name, patronymic))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Author surname is null or empty!");
    }

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки имени автора")
    public void shouldHaveCorrectExceptionForAuthorName() {
        long id = -1L;
        String surname = "surname";
        String name = "";
        String patronymic = "patronymic";


        when(authorDao.insert(any(Author.class))).thenReturn(id);

        assertThatThrownBy(() -> authorService.createAuthor(surname, name, patronymic))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Author name is null or empty!");
    }

    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedGenreCount() {
        when(authorDao.count()).thenReturn(EXPECTED_AUTHOR_COUNT);

        int actualGenreCount = authorService.countAuthors();
        assertThat(actualGenreCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        Author author = new Author(-1, "surname", "name", "patronymic");

        when(authorDao.getAll()).thenReturn(List.of(author));
        List<Author> actualList = authorService.getAllAuthors();

        assertThat(actualList).usingRecursiveFieldByFieldElementComparator().containsExactly(author);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(-1, "surname", "name", "patronymic");

        when(authorDao.getById(expectedAuthor.getId())).thenReturn(expectedAuthor);

        Author actualAuthor = authorService.getAuthorById(expectedAuthor.getId());

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }


    @DisplayName("добавлять автора")
    @Test
    void shouldCreateAuthor() {
        long id = -1L;
        String surname ="surname";
        String name = "name";
        String patronymic = "patronymic";

        when(authorDao.insert(any(Author.class))).thenReturn(id);

        Long actualId = authorService.createAuthor(surname, name, patronymic);

        assertThat(actualId).isEqualTo(id);
    }

    @DisplayName("обновлять автора по id")
    @Test
    void shouldUpdateAuthor() {
        long id = -1L;
        String surname ="surname";
        String name = "name";
        String patronymic = "patronymic";

        int expectedUpdatedCount = 1;

        when(authorDao.update(any(Author.class))).thenReturn(expectedUpdatedCount);
        int actualUpdatedCount = authorService.updateAuthor(id, surname, name, patronymic);

        assertThat(actualUpdatedCount).isEqualTo(expectedUpdatedCount);
    }

    @Test
    @DisplayName("удалять заданного автора по его id")
    void shouldCorrectlyDeleteAuthor() {
        long id = -1L;
        int expectedDeletedCount = 1;

        when(authorDao.deleteById(id)).thenReturn(expectedDeletedCount);
        int actualDeletedCount = authorService.deleteAuthorById(id);

        assertThat(actualDeletedCount).isEqualTo(expectedDeletedCount);
    }

}
