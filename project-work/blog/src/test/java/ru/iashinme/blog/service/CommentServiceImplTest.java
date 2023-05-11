package ru.iashinme.blog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.blog.dto.CommentDto;
import ru.iashinme.blog.dto.CommentRequestDto;
import ru.iashinme.blog.dto.CustomUserDetails;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.CommentMapper;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.model.Comment;
import ru.iashinme.blog.model.Post;
import ru.iashinme.blog.model.Technology;
import ru.iashinme.blog.repository.CommentRepository;
import ru.iashinme.blog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private static final CustomUserDetails USER = CustomUserDetails.builder().id(-1L).email("email")
            .authorities(List.of(new Authority(-1L, "ROLE_USER"))).fullName("fullname").build();
    private final static Post POST = Post.builder()
            .text("text")
            .technology(Technology.builder().id(-1L).name("name").build())
            .title("title")
            .author(USER.toUser())
            .build();

    private final static Comment COMMENT = Comment.builder()
            .id(-1L)
            .time(LocalDateTime.now())
            .author(USER.toUser())
            .text("text")
            .post(POST)
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
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .postId(-1L)
                .text("text")
                .build();

        when(postRepository.findById(any())).thenReturn(Optional.of(POST));
        when(commentRepository.save(any())).thenReturn(COMMENT);
        when(commentMapper.entityToDto(COMMENT)).thenReturn(EXPECTED_COMMENT_DTO);

        var actualCommentDto = commentService.save(commentRequestDto, USER);

        assertThat(actualCommentDto)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }

    @Test
    @DisplayName("корректно изменять комментарий")
    public void shouldHaveCorrectUpdateComment() {
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .postId(-1L)
                .text("text")
                .build();

        when(commentRepository.findById(any())).thenReturn(Optional.of(COMMENT));
        when(commentRepository.save(any())).thenReturn(COMMENT);
        when(commentMapper.entityToDto(COMMENT)).thenReturn(EXPECTED_COMMENT_DTO);

        var actualCommentDto = commentService.edit(commentRequestDto, USER);

        assertThat(actualCommentDto)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }

    @Test
    @DisplayName("корректно выкидывать исключение при попытки изменить не свой комментарий")
    public void shouldHaveCorrectReturnExceptionByUpdateComment() {
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .postId(-1L)
                .text("text")
                .build();

        when(commentRepository.findById(any())).thenReturn(Optional.of(COMMENT));

        CustomUserDetails otherUser = CustomUserDetails.builder()
                .id(-2L)
                .build();


        assertThatThrownBy(() -> commentService.edit(commentRequestDto, otherUser))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("You are not the author of the comment!");
    }

    @Test
    @DisplayName("корректно выкидывать исключение при попытки создать новый комментарий к несуществующему посту")
    public void shouldHaveCorrectReturnExceptionBySaveCommentWithPostNotExist() {
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .postId(-1L)
                .text("text")
                .build();

        assertThatThrownBy(() -> commentService.save(commentRequestDto, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Post not found!");
    }

    @Test
    @DisplayName("корректно выкидывать исключение при попытки изменить несуществующий комментарий")
    public void shouldHaveCorrectReturnExceptionByUpdateNotFoundComment() {
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .postId(-1L)
                .text("text")
                .build();

        assertThatThrownBy(() -> commentService.edit(commentRequestDto, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Comment not found!");
    }

    @Test
    @DisplayName("корректно удалять комментарий")
    public void shouldHaveCorrectDeleteComment() {
        commentService.delete(-1L);

        verify(commentRepository, times(1)).deleteById(-1L);
    }

    @Test
    @DisplayName("корректно находить комментарий по id")
    public void shouldHaveCorrectFindCommentById() {
        when(commentRepository.findById(any())).thenReturn(Optional.of(COMMENT));
        when(commentMapper.entityToDto(COMMENT)).thenReturn(EXPECTED_COMMENT_DTO);

        var comment = commentService.findById(COMMENT.getId());

        assertThat(comment).isEqualTo(EXPECTED_COMMENT_DTO);
    }

    @Test
    @DisplayName("корректно находить комментарий по id поста")
    public void shouldHaveCorrectFindCommentByPostId() {
        when(commentRepository.findByPostId(-1L)).thenReturn(List.of(EXPECTED_COMMENT_DTO));

        var comments = commentService.findAllByPostId(-1L);

        assertThat(comments).isEqualTo(List.of(EXPECTED_COMMENT_DTO));
    }
}
