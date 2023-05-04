package ru.iashinme.blog.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iashinme.blog.dto.CommentDto;
import ru.iashinme.blog.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Маппер для преобразования Comment(entity) в CommentDto ")
@SpringBootTest(classes = CommentMapper.class)
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    private static final User USER = User.builder()
            .id(-1L)
            .email("email")
            .authority(new Authority(-1L, "ROLE_USER"))
            .fullName("fullname")
            .build();

    private final static Post POST_ENTITY = Post.builder()
            .id(-1L)
            .technology(Technology.builder().id(-1L).name("name").build() )
            .title("title")
            .text("text")
            .author(User.builder().id(-1L).fullName("fullName").build())
            .build();
    private final static Comment COMMENT_ENTITY = Comment.builder()
            .id(-1L)
            .text("text")
            .time(LocalDateTime.of(2023, 1, 1, 0, 0))
            .author(USER)
            .post(POST_ENTITY)
            .build();

    private final static CommentDto COMMENT_DTO = CommentDto.builder()
            .id(COMMENT_ENTITY.getId())
            .authorFullName(USER.getFullName())
            .text(COMMENT_ENTITY.getText())
            .time(COMMENT_ENTITY.getTime())
            .build();

    @Test
    @DisplayName("должен возарвщать ожидаемый CommentDto")
    public void shouldCorrectReturnCommentDto() {

        CommentDto actualCommentDto = commentMapper.entityToDto(COMMENT_ENTITY);

        assertThat(actualCommentDto)
                .usingRecursiveComparison()
                .isEqualTo(COMMENT_DTO);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список CommentDto")
    public void shouldCorrectReturnListCommentDto() {

        List<CommentDto> actualCommentDto = commentMapper.entityToDto(List.of(COMMENT_ENTITY));

        assertThat(actualCommentDto)
                .usingRecursiveComparison()
                .isEqualTo(List.of(COMMENT_DTO));
    }
}
