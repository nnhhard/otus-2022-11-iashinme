package ru.iashin.homework06.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iashin.homework06.dto.CommentDto;
import ru.iashin.homework06.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Маппер для преобразования Comment(entity) в CommentDto ")
@SpringBootTest(classes = CommentMapper.class)
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    private static final Comment COMMENT_ENTITY_FIRST =
            new Comment(-1, -1, "message");
    private static final CommentDto EXPECTED_COMMENT_DTO_FIRST = CommentDto
            .builder()
            .id(COMMENT_ENTITY_FIRST.getId())
            .bookId(COMMENT_ENTITY_FIRST.getBookId())
            .messageComment(COMMENT_ENTITY_FIRST.getMessageComment())
            .time(COMMENT_ENTITY_FIRST.getTime())
            .build();

    private static final Comment COMMENT_ENTITY_SECOND =
            new Comment(-2, -1, "message2");
    private static final CommentDto EXPECTED_COMMENT_DTO_SECOND = CommentDto
            .builder()
            .id(COMMENT_ENTITY_SECOND.getId())
            .bookId(COMMENT_ENTITY_SECOND.getBookId())
            .messageComment(COMMENT_ENTITY_SECOND.getMessageComment())
            .time(COMMENT_ENTITY_SECOND.getTime())
            .build();

    @Test
    @DisplayName("должен возарвщать ожидаемый CommentDto")
    public void shouldCorrectReturnCommentDto() {
        CommentDto actualCommentDto = commentMapper.entityToDto(COMMENT_ENTITY_FIRST);
        assertThat(actualCommentDto)
                .usingRecursiveComparison()
                .isEqualTo(EXPECTED_COMMENT_DTO_FIRST);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список CommentDto")
    public void shouldCorrectReturnListCommentDto() {
        var actualCommentDtoList = commentMapper.entityToDto(
                List.of(COMMENT_ENTITY_FIRST, COMMENT_ENTITY_SECOND)
        );

        assertThat(actualCommentDtoList).
                containsExactlyInAnyOrderElementsOf(
                        List.of(EXPECTED_COMMENT_DTO_FIRST, EXPECTED_COMMENT_DTO_SECOND)
                );
    }
}
