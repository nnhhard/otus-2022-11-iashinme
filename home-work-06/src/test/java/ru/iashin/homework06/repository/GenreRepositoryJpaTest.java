package ru.iashin.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.iashin.homework06.model.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
public class GenreRepositoryJpaTest {
    private static final int EXPECTED_NUMBER_OF_GENRES = 3;
    private static final long FIRST_GENRE_ID = -3L;

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном жанре по его id")
    @Test
    void shouldFindExpectedGenreById() {
        Optional<Genre> optionalActualGenre = genreRepositoryJpa.findById(FIRST_GENRE_ID);
        Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);

        assertThat(optionalActualGenre)
                .isPresent()
                .get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        var genres = genreRepositoryJpa.findAll();
        assertThat(genres)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(s -> !s.getName().equals(""));
    }

    @DisplayName("считать общее количество жанров")
    @Test
    void shouldCalcGenresCount() {
        long genresCount = genreRepositoryJpa.count();
        assertThat(genresCount)
                .isEqualTo(EXPECTED_NUMBER_OF_GENRES);
    }

    @DisplayName("удалять жанр по его id")
    @Test
    void shouldDeleteGenreById() {
        Genre genre = em.find(Genre.class, FIRST_GENRE_ID);
        em.detach(genre);

        genreRepositoryJpa.deleteById(genre.getId());
        var findGenre = genreRepositoryJpa.findById(genre.getId());

        assertThat(findGenre)
                .isNotPresent();
    }

    @DisplayName("изменять наименование жанра")
    @Test
    void shouldUpdateGenreName() {
        Genre genre = em.find(Genre.class, FIRST_GENRE_ID);
        String oldName = genre.getName();
        String newName = "Драма";
        em.detach(genre);
        genre.setName(newName);

        Genre actualGenre = genreRepositoryJpa.save(genre);

        assertThat(actualGenre.getName())
                .isNotEqualTo(oldName)
                .isEqualTo(newName);
    }
}
