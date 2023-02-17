package ru.iashinme.homework10.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iashinme.homework10.dto.GenreDto;
import ru.iashinme.homework10.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Маппер для преобразования Genre(entity) в GenreDto ")
@SpringBootTest(classes = GenreMapper.class)
public class GenreMapperTest {

    @Autowired
    private GenreMapper genreMapper;

    private static final Genre GENRE_ENTITY_FIRST = new Genre(-1, "Поэма");
    private static final Genre GENRE_ENTITY_SECOND = new Genre(-2, "Стихи");
    private static final GenreDto EXPECTED_GENRE_DTO_FIRST = GenreDto
            .builder()
            .id(GENRE_ENTITY_FIRST.getId())
            .name(GENRE_ENTITY_FIRST.getName())
            .build();
    private static final GenreDto EXPECTED_GENRE_DTO_SECOND = GenreDto
            .builder()
            .id(GENRE_ENTITY_SECOND.getId())
            .name(GENRE_ENTITY_SECOND.getName())
            .build();

    @Test
    @DisplayName("должен возарвщать ожидаемый GenreDto ")
    public void shouldCorrectReturnGenreDto() {
        GenreDto actualGenreDto = genreMapper.entityToDto(GENRE_ENTITY_FIRST);
        assertThat(actualGenreDto).usingRecursiveComparison().isEqualTo(EXPECTED_GENRE_DTO_FIRST);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список GenreDto ")
    public void shouldCorrectReturnListGenreDto() {
        var actualGenreDtoList = genreMapper.entityToDto(List.of(GENRE_ENTITY_FIRST, GENRE_ENTITY_SECOND));

        assertThat(actualGenreDtoList).
                containsExactlyInAnyOrderElementsOf(
                        List.of(EXPECTED_GENRE_DTO_FIRST, EXPECTED_GENRE_DTO_SECOND)
                );
    }
}
