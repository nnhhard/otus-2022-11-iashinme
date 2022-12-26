package ru.iashinme.homework05.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework05.dao.GenreDao;
import ru.iashinme.homework05.domain.Genre;
import ru.iashinme.homework05.exception.ValidateException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с жанрами должен ")
@SpringBootTest(classes = GenreServiceImpl.class)
public class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @MockBean
    private GenreDao genreDao;

    private static final int EXPECTED_GENRES_COUNT = 1;

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки наименования жанра")
    public void shouldHaveCorrectExceptionForGenreName() {
        long id = -1L;
        String name = "";

        when(genreDao.insert(any(Genre.class))).thenReturn(id);

        assertThatThrownBy(() -> genreService.createGenre(name))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Genre name is null or empty!");
    }

    @DisplayName("возвращать ожидаемое количество жанров")
    @Test
    void shouldReturnExpectedGenreCount() {
        when(genreDao.count()).thenReturn(EXPECTED_GENRES_COUNT);

        int actualGenreCount = genreService.countGenres();
        assertThat(actualGenreCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        Genre genre = new Genre(-1, "Поэма");

        when(genreDao.getAll()).thenReturn(List.of(genre));
        List<Genre> actualList = genreService.getAllGenres();

        assertThat(actualList).usingRecursiveFieldByFieldElementComparator().containsExactly(genre);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(-1, "Поэма");

        when(genreDao.getById(expectedGenre.getId())).thenReturn(expectedGenre);

        Genre actualGenre = genreService.getGenreById(expectedGenre.getId());

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }


    @DisplayName("добавлять жанр")
    @Test
    void shouldCreateGenre() {
        long id = -1L;
        String name = "Сказка";

        when(genreDao.insert(any(Genre.class))).thenReturn(id);

        Long actualId = genreService.createGenre(name);

        assertThat(actualId).isEqualTo(id);
    }

    @DisplayName("обновлять жанр по id")
    @Test
    void shouldUpdateGenre() {
        long id = -1L;
        String name = "Сказка";

        int expectedUpdatedCount = 1;

        when(genreDao.update(any(Genre.class))).thenReturn(expectedUpdatedCount);
        int actualUpdatedCount = genreService.updateGenre(id, name);

        assertThat(actualUpdatedCount).isEqualTo(expectedUpdatedCount);
    }

    @Test
    @DisplayName("удалять заданный жанр по его id")
    void shouldCorrectlyDeleteGenre() {
        long id = -1L;
        int expectedDeletedCount = 1;

        when(genreDao.deleteById(id)).thenReturn(expectedDeletedCount);
        int actualDeletedCount = genreService.deleteGenreById(id);

        assertThat(actualDeletedCount).isEqualTo(expectedDeletedCount);
    }

}
