package ru.iashinme.homework08.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iashinme.homework08.dto.CommentDto;
import ru.iashinme.homework08.model.Book;
import ru.iashinme.homework08.model.Comment;
import ru.iashinme.homework08.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Маппер для преобразования Comment(entity) в CommentWithoutBookDto ")
@SpringBootTest(classes = CommentMapper.class)
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    private final static Genre GENRE_ENTITY = new Genre("-1", "genre");
    private final static Book BOOK_ENTITY = new Book("-1", "name", GENRE_ENTITY);
    private final static Comment COMMENT_ENTITY = new Comment("-1", BOOK_ENTITY, "comment");

    private final static CommentDto EXPECTED_COMMENT_WITHOUT_BOOK_DTO = CommentDto
            .builder()
            .id(COMMENT_ENTITY.getId())
            .time(COMMENT_ENTITY.getTime())
            .messageComment(COMMENT_ENTITY.getMessageComment())
            .bookId(COMMENT_ENTITY.getBook().getId())
            .build();

    @Test
    @DisplayName("должен возарвщать ожидаемый CommentWithoutBookDto")
    public void shouldCorrectReturnCommentWithoutBookDto() {
        CommentDto actualCommentDto = commentMapper.entityToDto(COMMENT_ENTITY);
        assertThat(actualCommentDto)
                .usingRecursiveComparison()
                .isEqualTo(EXPECTED_COMMENT_WITHOUT_BOOK_DTO);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список CommentWithoutBookDto")
    public void shouldCorrectReturnListCommentWithoutBookDto() {
        var actualCommentDtoList = commentMapper.entityToDto(
                List.of(COMMENT_ENTITY)
        );

        assertThat(actualCommentDtoList).
                containsExactlyInAnyOrderElementsOf(
                        List.of(EXPECTED_COMMENT_WITHOUT_BOOK_DTO)
                );
    }
}
