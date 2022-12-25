package ru.iashinme.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.iashinme.homework05.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_GENRES_COUNT = 1;
    private static final int EXISTING_GENRE_ID = -1;
    private static final String EXISTING_GENRE_NAME = "Поэма";

    @Autowired
    private GenreDaoJdbc genreDao;

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        int actualGenreCount = genreDao.count();
        assertThat(actualGenreCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre("Драма");
        var id = genreDao.insert(expectedGenre);
        expectedGenre.setId(id);
        var actualGenre = genreDao.getById(id);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }


    @DisplayName("удалять заданный жанр по его id")
    @Test
    void shouldCorrectDeleteGenreById() {
        Genre genre = new Genre( "Драма");
        var id = genreDao.insert(genre);

        assertThatCode(() -> genreDao.getById(id))
                .doesNotThrowAnyException();

        var numberDeleteRow = genreDao.deleteById(id);

        assertThat(numberDeleteRow).isEqualTo(1);

        assertThatThrownBy(() -> genreDao.getById(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = genreDao.getAll();
        assertThat(actualGenreList)
                .hasSize(EXPECTED_GENRES_COUNT)
                .containsExactlyInAnyOrder(expectedGenre);
    }

    @DisplayName("обновлять жанр по id")
    @Test
    void shouldUpdateGenreById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, "new name");
        var numberUpdateRow = genreDao.update(expectedGenre);
        assertThat(numberUpdateRow).isEqualTo(1);

        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre)
                .isEqualTo(expectedGenre);
    }
}