package ru.iashin.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.iashin.homework06.model.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {
    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private static final long FIRST_AUTHOR_ID = -2L;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о нужном авторе по его id")
    @Test
    void shouldFindExpectedAuthorById() {
        Optional<Author> optionalActualAuthor = authorRepositoryJpa.findById(FIRST_AUTHOR_ID);
        Author expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        var authors = authorRepositoryJpa.findAll();
        assertThat(authors)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(s -> !s.getName().equals(""));
    }

    @DisplayName("считать общее количество авторов")
    @Test
    void shouldCalcAuthorsCount() {
        long authorsCount = authorRepositoryJpa.count();
        assertThat(authorsCount).isEqualTo(EXPECTED_NUMBER_OF_AUTHORS);
    }

    @DisplayName("удалять автора по его id")
    @Test
    void shouldDeleteAuthorById() {
        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        em.detach(author);

        authorRepositoryJpa.deleteById(author.getId());
        var findAuthor = authorRepositoryJpa.findById(author.getId());

        assertThat(findAuthor).isNotPresent();
    }

    @DisplayName("изменять автора по его id")
    @Test
    void shouldUpdateAuthorById() {
        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        String oldSurname = author.getSurname();
        String oldName = author.getName();
        String oldPatronymic = author.getPatronymic();

        String newSurname = "Пушкин";
        String newName = "Александр";
        String newPatronymic = "Сергеевич";

        em.detach(author);

        author.setSurname(newSurname);
        author.setName(newName);
        author.setPatronymic(newPatronymic);

        Author actualAuthor = authorRepositoryJpa.save(author);

        assertThat(actualAuthor.getName())
                .isNotEqualTo(oldName)
                .isEqualTo(newName);
        assertThat(actualAuthor.getSurname())
                .isNotEqualTo(oldSurname)
                .isEqualTo(newSurname);
        assertThat(actualAuthor.getPatronymic())
                .isNotEqualTo(oldPatronymic)
                .isEqualTo(newPatronymic);
    }
}
