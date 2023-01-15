package ru.iashin.homework06.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashin.homework06.dto.BookWithAllInfoDto;
import ru.iashin.homework06.dto.CommentDto;
import ru.iashin.homework06.exception.ValidateException;
import ru.iashin.homework06.mapper.CommentMapper;
import ru.iashin.homework06.model.Comment;
import ru.iashin.homework06.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с комменатриями должен ")
@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceImplTest {

    private final static Comment EXPECTED_COMMENT = new Comment(-1,-1, "comment");
    private final static CommentDto EXPECTED_COMMENT_DTO = CommentDto
            .builder()
            .id(EXPECTED_COMMENT.getId())
            .bookId(EXPECTED_COMMENT.getBookId())
            .time(EXPECTED_COMMENT.getTime())
            .messageComment(EXPECTED_COMMENT.getMessageComment())
            .build();

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentMapper commentMapper;

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки сообщения комментария")
    public void shouldHaveCorrectExceptionForCheckMessageComment() {
        when(bookService.getBookById(any(Long.class))).thenReturn(
                BookWithAllInfoDto.builder()
                        .id(-1)
                        .name("name")
                        .build()
        );

        assertThatThrownBy(() -> commentService.createComment(-1, ""))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("he comment message cannot be empty or null");
    }


    @DisplayName("возвращать ожидаемый список комментарий для книги по ее id")
    @Test
    void shouldReturnExpectedCommentList() {
        when(commentRepository.findByBookId(any(Long.class))).thenReturn(List.of(EXPECTED_COMMENT));
        when(commentMapper.entityToDto(List.of(EXPECTED_COMMENT))).thenReturn(List.of(EXPECTED_COMMENT_DTO));

        var actualList = commentService.getAllCommentsByBookId(EXPECTED_COMMENT.getId());

        assertThat(actualList).usingRecursiveFieldByFieldElementComparator().containsExactly(EXPECTED_COMMENT_DTO);
    }

    @DisplayName("возвращать ожидаемомый комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(EXPECTED_COMMENT));
        when(commentMapper.entityToDto(any(Comment.class))).thenReturn(EXPECTED_COMMENT_DTO);

        var actualComment = commentService.getCommentById(EXPECTED_COMMENT.getId());

        assertThat(actualComment)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }

    @DisplayName("добавлять комментарий")
    @Test
    void shouldCreateComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(EXPECTED_COMMENT);
        when(commentMapper.entityToDto(any(Comment.class))).thenReturn(EXPECTED_COMMENT_DTO);
        when(bookService.getBookById(any(Long.class))).thenReturn(
                BookWithAllInfoDto.builder()
                        .id(-1)
                        .name("name")
                        .build()
        );

        var actualComment = commentService.createComment(EXPECTED_COMMENT.getId(), EXPECTED_COMMENT.getMessageComment());

        assertThat(actualComment)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }


    @DisplayName("обновлять коментарий по id")
    @Test
    void shouldUpdateComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(EXPECTED_COMMENT);
        when(commentMapper.entityToDto(any(Comment.class))).thenReturn(EXPECTED_COMMENT_DTO);
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(EXPECTED_COMMENT));

        var actualComment = commentService.updateComment(EXPECTED_COMMENT.getId(), EXPECTED_COMMENT.getMessageComment());

        assertThat(actualComment)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }

    @Test
    @DisplayName("корректно кидать исключение при попытке удалить несуществующий комментарий")
    void shouldCorrectlyExceptionByDeleteComment() {
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.deleteCommentById(-1))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Comment not find with id = " + -1);
    }
}
