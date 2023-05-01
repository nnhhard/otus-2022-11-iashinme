package ru.iashinme.blog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.blog.dto.CommentDto;
import ru.iashinme.blog.dto.CommentRequestDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.CommentMapper;
import ru.iashinme.blog.model.Comment;
import ru.iashinme.blog.model.Post;
import ru.iashinme.blog.model.Technology;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.repository.CommentRepository;
import ru.iashinme.blog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с комментариями ")
@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private CommentMapper commentMapper;

    private static final User USER = User.builder().id(-1L).email("email").fullName("fullname").build();
    private final static Post POST = Post.builder()
            .text("text")
            .technology(Technology.builder().id(-1L).name("name").build())
            .title("title")
            .author(USER)
            .build();

    private final static Comment COMMENT = Comment.builder()
            .id(-1L)
            .time(LocalDateTime.now())
            .author(USER)
            .text("text")
            .post(POST)
            .build();
    private final static CommentRequestDto REQUEST_COMMENT_DTO = CommentRequestDto.builder()
            .postId(-1L)
            .text("text")
            .build();

    private final static CommentDto EXPECTED_COMMENT_DTO = CommentDto.builder()
            .time(LocalDateTime.now())
            .authorFullName(USER.getFullName())
            .text("text")
            .id(-1L)
            .build();
    @Test
    @DisplayName("корректно сохранять комментарий")
    public void shouldHaveCorrectSaveComment() {
        when(postRepository.findById(any())).thenReturn(Optional.of(POST));
        when(commentRepository.save(any())).thenReturn(COMMENT);
        when(commentMapper.entityToDto(COMMENT)).thenReturn(EXPECTED_COMMENT_DTO);

        var actualCommentDto = commentService.save(REQUEST_COMMENT_DTO, USER);

        assertThat(actualCommentDto)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }

    @Test
    @DisplayName("корректно изменять комментарий")
    public void shouldHaveCorrectUpdateComment() {
        when(commentRepository.findById(any())).thenReturn(Optional.of(COMMENT));

        Comment updateComment = COMMENT;
        updateComment.setText("new text");

        when(commentRepository.save(any())).thenReturn(updateComment);
        when(commentMapper.entityToDto(updateComment)).thenReturn(EXPECTED_COMMENT_DTO);

        var actualCommentDto = commentService.edit(REQUEST_COMMENT_DTO, USER);

        assertThat(actualCommentDto)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }

    @Test
    @DisplayName("корректно выкидывать исключение при попытки изменить не свой комментарий")
    public void shouldHaveCorrectReturnExceptionByUpdateComment() {
        when(commentRepository.findById(any())).thenReturn(Optional.of(COMMENT));

        User otherUser = User.builder()
                .id(-2L)
                .build();


        assertThatThrownBy(() -> commentService.edit(REQUEST_COMMENT_DTO, otherUser))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("You are not the author of the comment!");
    }

    @Test
    @DisplayName("корректно выкидывать исключение при попытки создать новый комментарий к несуществующему посту")
    public void shouldHaveCorrectReturnExceptionBySaveCommentWithPostNotExist() {
        assertThatThrownBy(() -> commentService.save(REQUEST_COMMENT_DTO, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Post not found!");
    }

    @Test
    @DisplayName("корректно выкидывать исключение при попытки изменить несуществующий комментарий")
    public void shouldHaveCorrectReturnExceptionByUpdateNotFoundComment() {
        assertThatThrownBy(() -> commentService.edit(REQUEST_COMMENT_DTO, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Comment not found!");
    }

}
