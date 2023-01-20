package ru.iashin.homework06.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashin.homework06.dto.AuthorDto;
import ru.iashin.homework06.dto.BookWithAllInfoDto;
import ru.iashin.homework06.dto.GenreDto;
import ru.iashin.homework06.model.Author;
import ru.iashin.homework06.model.Book;
import ru.iashin.homework06.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Маппер для преобразования Book(entity) в BookWithAllInfoDto ")
@SpringBootTest(classes = BookWithAllInfoMapper.class)
public class BookWithAllInfoTest {

    @Autowired
    private BookWithAllInfoMapper bookWithAllInfoMapper;

    @MockBean
    private AuthorMapper authorMapper;

    @MockBean
    private GenreMapper genreMapper;

    private static final Author AUTHOR_ENTITY_FIRST =
            new Author(-1, "surname", "name", "patron");
    private static final Genre GENRE_ENTITY_FIRST = new Genre(-1, "Поэма");

    private static final Book BOOK_ENTITY_FIRST = new Book(
            -1,
            "bookName",
            List.of(AUTHOR_ENTITY_FIRST),
            GENRE_ENTITY_FIRST
    );

    private static final GenreDto EXPECTED_GENRE_DTO_FIRST = GenreDto
            .builder()
            .id(GENRE_ENTITY_FIRST.getId())
            .name(GENRE_ENTITY_FIRST.getName())
            .build();
    private static final AuthorDto EXPECTED_AUTHOR_DTO_FIRST = AuthorDto
            .builder()
            .id(AUTHOR_ENTITY_FIRST.getId())
            .surname(AUTHOR_ENTITY_FIRST.getSurname())
            .name(AUTHOR_ENTITY_FIRST.getName())
            .patronymic(AUTHOR_ENTITY_FIRST.getPatronymic())
            .build();
    private static final BookWithAllInfoDto EXPECTED_BOOK_DTO_FIRST = BookWithAllInfoDto
            .builder()
            .id(BOOK_ENTITY_FIRST.getId())
            .name(BOOK_ENTITY_FIRST.getName())
            .genre(EXPECTED_GENRE_DTO_FIRST)
            .authors(List.of(EXPECTED_AUTHOR_DTO_FIRST))
            .build();


    @Test
    @DisplayName("должен возарвщать ожидаемый BookWithAllInfoDto")
    public void shouldCorrectReturnBookWithAllInfoDto() {
        when(authorMapper.entityToDto(List.of(AUTHOR_ENTITY_FIRST))).thenReturn(List.of(EXPECTED_AUTHOR_DTO_FIRST));
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO_FIRST);

        var actualBookDto = bookWithAllInfoMapper.entityToDto(BOOK_ENTITY_FIRST);
        assertThat(actualBookDto)
                .usingRecursiveComparison()
                .isEqualTo(EXPECTED_BOOK_DTO_FIRST);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список BookWithAllInfoDto")
    public void shouldCorrectReturnListBookWithAllInfoDto() {
        when(authorMapper.entityToDto(List.of(AUTHOR_ENTITY_FIRST))).thenReturn(List.of(EXPECTED_AUTHOR_DTO_FIRST));
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO_FIRST);

        var actualBookDtoList = bookWithAllInfoMapper.entityToDto(
                List.of(BOOK_ENTITY_FIRST)
        );

        assertThat(actualBookDtoList).
                containsExactlyInAnyOrderElementsOf(
                        List.of(EXPECTED_BOOK_DTO_FIRST)
                );
    }
}
