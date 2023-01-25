package ru.iashinme.homework08.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework08.dto.BookWithIdNameGenreDto;
import ru.iashinme.homework08.dto.GenreDto;
import ru.iashinme.homework08.model.Book;
import ru.iashinme.homework08.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Маппер для преобразования Book(entity) в BookWithIdNameGenreDto ")
@SpringBootTest(classes = BookWithIdNameGenreMapper.class)
public class BookWithIdNameGenreTest {

    @Autowired
    private BookWithIdNameGenreMapper bookWithIdNameGenreMapper;

    @MockBean
    private GenreMapper genreMapper;

    private static final Genre GENRE_ENTITY_FIRST = new Genre("-1", "Поэма");
    private static final Book BOOK_ENTITY_FIRST = new Book(
            "-1",
            "bookName",
            GENRE_ENTITY_FIRST
    );
    private static final GenreDto EXPECTED_GENRE_DTO_FIRST = GenreDto
            .builder()
            .id(GENRE_ENTITY_FIRST.getId())
            .name(GENRE_ENTITY_FIRST.getName())
            .build();
    private static final BookWithIdNameGenreDto EXPECTED_BOOK_DTO_FIRST = BookWithIdNameGenreDto
            .builder()
            .id(BOOK_ENTITY_FIRST.getId())
            .name(BOOK_ENTITY_FIRST.getName())
            .genre(EXPECTED_GENRE_DTO_FIRST)
            .build();


    @Test
    @DisplayName("должен возарвщать ожидаемый BookWithIdNameGenreDto")
    public void shouldCorrectReturnBookWithIdNameGenreDto() {
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO_FIRST);

        var actualBookDto = bookWithIdNameGenreMapper.entityToDto(BOOK_ENTITY_FIRST);
        assertThat(actualBookDto)
                .usingRecursiveComparison()
                .isEqualTo(EXPECTED_BOOK_DTO_FIRST);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список BookWithIdNameGenreDto")
    public void shouldCorrectReturnListBookWithIdNameGenreDto() {
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO_FIRST);

        var actualBookDtoList = bookWithIdNameGenreMapper.entityToDto(
                List.of(BOOK_ENTITY_FIRST)
        );

        assertThat(actualBookDtoList).
                containsExactlyInAnyOrderElementsOf(
                        List.of(EXPECTED_BOOK_DTO_FIRST)
                );
    }

}
