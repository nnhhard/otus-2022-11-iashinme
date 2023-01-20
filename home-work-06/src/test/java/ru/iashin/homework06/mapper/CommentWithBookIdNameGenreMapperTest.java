package ru.iashin.homework06.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashin.homework06.dto.BookWithIdNameGenreDto;
import ru.iashin.homework06.dto.CommentWithBookIdNameGenreDto;
import ru.iashin.homework06.dto.GenreDto;
import ru.iashin.homework06.model.Book;
import ru.iashin.homework06.model.Comment;
import ru.iashin.homework06.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Маппер для преобразования Comment(entity) в CommentWithBookIdNameGenreDto ")
@SpringBootTest(classes = CommentWithBookIdNameGenreMapper.class)
public class CommentWithBookIdNameGenreMapperTest {

    @Autowired
    private CommentWithBookIdNameGenreMapper commentMapper;

    @MockBean
    private BookWithIdNameGenreMapper bookWithIdNameGenreMapper;

    private final static Genre GENRE_ENTITY = new Genre(-1, "genre");
    private final static GenreDto GENRE_DTO = GenreDto.builder()
            .id(GENRE_ENTITY.getId())
            .name(GENRE_ENTITY.getName())
            .build();

    private final static Book BOOK_ENTITY = new Book(-1, "name", GENRE_ENTITY);
    private final static BookWithIdNameGenreDto BOOK_DTO = BookWithIdNameGenreDto
            .builder()
            .id(BOOK_ENTITY.getId())
            .name(BOOK_ENTITY.getName())
            .genre(GENRE_DTO)
            .build();


    private final static Comment COMMENT_ENTITY = new Comment(-1, BOOK_ENTITY, "comment");
    private final static CommentWithBookIdNameGenreDto EXPECTED_COMMENT_DTO = CommentWithBookIdNameGenreDto
            .builder()
            .id(COMMENT_ENTITY.getId())
            .book(BOOK_DTO)
            .time(COMMENT_ENTITY.getTime())
            .messageComment(COMMENT_ENTITY.getMessageComment())
            .build();


    @Test
    @DisplayName("должен возарвщать ожидаемый CommentWithBookIdNameGenreDto")
    public void shouldCorrectReturnCommentWithBookIdNameGenreDto() {
        when(bookWithIdNameGenreMapper.entityToDto(BOOK_ENTITY)).thenReturn(BOOK_DTO);

        CommentWithBookIdNameGenreDto actualCommentDto = commentMapper.entityToDto(COMMENT_ENTITY);
        assertThat(actualCommentDto)
                .usingRecursiveComparison()
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список CommentWithoutBookDto")
    public void shouldCorrectReturnListCommentWithBookIdNameGenreDto() {
        when(bookWithIdNameGenreMapper.entityToDto(BOOK_ENTITY)).thenReturn(BOOK_DTO);
        var actualCommentDtoList = commentMapper.entityToDto(
                List.of(COMMENT_ENTITY)
        );

        assertThat(actualCommentDtoList).
                containsExactlyInAnyOrderElementsOf(
                        List.of(EXPECTED_COMMENT_DTO)
                );
    }
}
