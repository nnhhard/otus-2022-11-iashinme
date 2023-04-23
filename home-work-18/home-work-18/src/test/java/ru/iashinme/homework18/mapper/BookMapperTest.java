package ru.iashinme.homework18.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework18.dto.AuthorDto;
import ru.iashinme.homework18.dto.BookDto;
import ru.iashinme.homework18.dto.GenreDto;
import ru.iashinme.homework18.model.Author;
import ru.iashinme.homework18.model.Book;
import ru.iashinme.homework18.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Маппер для преобразования Book(entity) в BookWithAllInfoDto ")
@SpringBootTest(classes = BookMapper.class)
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

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
            AUTHOR_ENTITY_FIRST,
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
    private static final BookDto EXPECTED_BOOK_DTO_FIRST = BookDto
            .builder()
            .id(BOOK_ENTITY_FIRST.getId())
            .name(BOOK_ENTITY_FIRST.getName())
            .genre(EXPECTED_GENRE_DTO_FIRST)
            .author(EXPECTED_AUTHOR_DTO_FIRST)
            .build();


    @Test
    @DisplayName("должен возарвщать ожидаемый BookWithAllInfoDto")
    public void shouldCorrectReturnBookWithAllInfoDto() {
        when(authorMapper.entityToDto(AUTHOR_ENTITY_FIRST)).thenReturn(EXPECTED_AUTHOR_DTO_FIRST);
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO_FIRST);

        var actualBookDto = bookMapper.entityToDto(BOOK_ENTITY_FIRST);
        assertThat(actualBookDto)
                .isEqualTo(EXPECTED_BOOK_DTO_FIRST);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список BookWithAllInfoDto")
    public void shouldCorrectReturnListBookWithAllInfoDto() {
        when(authorMapper.entityToDto(AUTHOR_ENTITY_FIRST)).thenReturn(EXPECTED_AUTHOR_DTO_FIRST);
        when(genreMapper.entityToDto(any(Genre.class))).thenReturn(EXPECTED_GENRE_DTO_FIRST);

        var actualBookDtoList = bookMapper.entityToDto(
                List.of(BOOK_ENTITY_FIRST)
        );

        assertThat(actualBookDtoList).
                containsExactlyInAnyOrderElementsOf(
                        List.of(EXPECTED_BOOK_DTO_FIRST)
                );
    }
}
