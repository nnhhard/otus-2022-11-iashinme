package ru.iashinme.homework17.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework17.dto.BookDto;
import ru.iashinme.homework17.dto.CommentDto;
import ru.iashinme.homework17.dto.GenreDto;
import ru.iashinme.homework17.model.Author;
import ru.iashinme.homework17.model.Book;
import ru.iashinme.homework17.model.Comment;
import ru.iashinme.homework17.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Маппер для преобразования Comment(entity) в CommentWithBookIdNameGenreDto ")
@SpringBootTest(classes = CommentMapper.class)
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @MockBean
    private BookMapper bookWithIdNameGenreMapper;

    private static final Author AUTHOR_ENTITY =
            new Author(-1, "surname", "name", "patron");
    private final static Genre GENRE_ENTITY = new Genre(-1, "genre");
    private final static GenreDto GENRE_DTO = GenreDto.builder()
            .id(GENRE_ENTITY.getId())
            .name(GENRE_ENTITY.getName())
            .build();

    private final static Book BOOK_ENTITY = new Book(-1, "name", AUTHOR_ENTITY, GENRE_ENTITY);
    private final static BookDto BOOK_DTO = BookDto
            .builder()
            .id(BOOK_ENTITY.getId())
            .name(BOOK_ENTITY.getName())
            .genre(GENRE_DTO)
            .build();


    private final static Comment COMMENT_ENTITY = new Comment(-1, BOOK_ENTITY, "comment");
    private final static CommentDto EXPECTED_COMMENT_DTO = CommentDto
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

        CommentDto actualCommentDto = commentMapper.entityToDto(COMMENT_ENTITY);
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
