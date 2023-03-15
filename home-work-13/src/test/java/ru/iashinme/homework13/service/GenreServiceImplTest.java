package ru.iashinme.homework13.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework13.dto.GenreDto;
import ru.iashinme.homework13.exception.ValidateException;
import ru.iashinme.homework13.mapper.GenreMapper;
import ru.iashinme.homework13.model.Genre;
import ru.iashinme.homework13.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с жанрами должен ")
@SpringBootTest(classes = GenreServiceImpl.class)
public class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private GenreMapper genreMapper;

    private static final Genre EXPECTED_GENRE = new Genre(-1L, "Сказка");
    private static final GenreDto EXPECTED_GENRE_DTO = GenreDto
            .builder()
            .id(EXPECTED_GENRE.getId())
            .name(EXPECTED_GENRE.getName())
            .build();

    private static final long EXPECTED_GENRES_COUNT = 1;

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки наименования жанра")
    public void shouldHaveCorrectExceptionForGenreName() {
        String emptyName = "";
        when(genreRepository.save(any(Genre.class))).thenReturn(EXPECTED_GENRE);

        assertThatThrownBy(() -> genreService.create(emptyName))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Genre name is null or empty!");
    }

    @DisplayName("возвращать ожидаемое количество жанров")
    @Test
    void shouldReturnExpectedGenreCount() {
        when(genreRepository.count()).thenReturn(EXPECTED_GENRES_COUNT);
        long actualGenreCount = genreService.counts();

        assertThat(actualGenreCount)
                .isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        when(genreRepository.findAll()).thenReturn(List.of(EXPECTED_GENRE));
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO);
        var actualList = genreService.findAll();

        assertThat(actualList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(EXPECTED_GENRE_DTO);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        when(genreRepository.findById(any(Long.class))).thenReturn(Optional.of(EXPECTED_GENRE));
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO);
        var actualGenre = genreService.findById(EXPECTED_GENRE.getId());

        assertThat(actualGenre)
                .isEqualTo(EXPECTED_GENRE_DTO);
    }

    @DisplayName("добавлять жанр")
    @Test
    void shouldCreateGenre() {
        when(genreRepository.save(any(Genre.class))).thenReturn(EXPECTED_GENRE);
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO);
        var actualGenre = genreService.create(EXPECTED_GENRE.getName());

        assertThat(actualGenre)
                .isEqualTo(EXPECTED_GENRE_DTO);
    }

    @DisplayName("обновлять жанр по id")
    @Test
    void shouldUpdateGenre() {
        when(genreRepository.save(any(Genre.class))).thenReturn(EXPECTED_GENRE);
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO);

        var actualGenre = genreService.update(EXPECTED_GENRE.getId(), EXPECTED_GENRE.getName());

        assertThat(actualGenre)
                .isEqualTo(EXPECTED_GENRE_DTO);
    }
}
