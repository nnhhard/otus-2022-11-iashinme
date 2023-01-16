package ru.iashin.homework06.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iashin.homework06.dto.CommentWithoutBookDto;
import ru.iashin.homework06.model.Book;
import ru.iashin.homework06.model.Comment;
import ru.iashin.homework06.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Маппер для преобразования Comment(entity) в CommentWithoutBookDto ")
@SpringBootTest(classes = CommentWithoutBookMapper.class)
public class CommentWithoutBookMapperTest {

    @Autowired
    private CommentWithoutBookMapper commentMapper;

    private final static Genre GENRE_ENTITY = new Genre(-1, "genre");
    private final static Book BOOK_ENTITY = new Book(-1, "name", GENRE_ENTITY);
    private final static Comment COMMENT_ENTITY = new Comment(-1, BOOK_ENTITY, "comment");

    private final static CommentWithoutBookDto EXPECTED_COMMENT_WITHOUT_BOOK_DTO = CommentWithoutBookDto
            .builder()
            .id(COMMENT_ENTITY.getId())
            .time(COMMENT_ENTITY.getTime())
            .messageComment(COMMENT_ENTITY.getMessageComment())
            .build();

    @Test
    @DisplayName("должен возарвщать ожидаемый CommentWithoutBookDto")
    public void shouldCorrectReturnCommentWithoutBookDto() {
        CommentWithoutBookDto actualCommentDto = commentMapper.entityToDto(COMMENT_ENTITY);
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
