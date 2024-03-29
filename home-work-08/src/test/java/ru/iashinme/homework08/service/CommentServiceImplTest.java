package ru.iashinme.homework08.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework08.dto.BookWithIdNameGenreDto;
import ru.iashinme.homework08.dto.CommentDto;
import ru.iashinme.homework08.dto.GenreDto;
import ru.iashinme.homework08.exception.ValidateException;
import ru.iashinme.homework08.mapper.CommentMapper;
import ru.iashinme.homework08.model.Book;
import ru.iashinme.homework08.model.Comment;
import ru.iashinme.homework08.model.Genre;
import ru.iashinme.homework08.repository.BookRepository;
import ru.iashinme.homework08.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с комменатриями должен ")
@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceImplTest {

    private final static Genre GENRE_ENTITY = new Genre("-1", "genre");
    private final static GenreDto GENRE_DTO = GenreDto.builder()
            .id(GENRE_ENTITY.getId())
            .name(GENRE_ENTITY.getName())
            .build();

    private final static Book BOOK_ENTITY = new Book("-1", "name", GENRE_ENTITY);
    private final static BookWithIdNameGenreDto BOOK_DTO = BookWithIdNameGenreDto
            .builder()
            .id(BOOK_ENTITY.getId())
            .name(BOOK_ENTITY.getName())
            .genre(GENRE_DTO)
            .build();

    private final static Comment COMMENT_ENTITY = new Comment("-1", BOOK_ENTITY, "comment");
    private final static CommentDto EXPECTED_COMMENT_DTO = CommentDto
            .builder()
            .id(COMMENT_ENTITY.getId())
            .bookId(BOOK_DTO.getId())
            .time(COMMENT_ENTITY.getTime())
            .messageComment(COMMENT_ENTITY.getMessageComment())
            .build();

    private final static CommentDto EXPECTED_COMMENT_WITHOUT_BOOK_DTO = CommentDto
            .builder()
            .id(COMMENT_ENTITY.getId())
            .time(COMMENT_ENTITY.getTime())
            .messageComment(COMMENT_ENTITY.getMessageComment())
            .build();

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentMapper commentMapper;

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки сообщения комментария")
    public void shouldHaveCorrectExceptionForCheckMessageComment() {
        when(bookRepository.findById(any(String.class))).thenReturn(Optional.of(BOOK_ENTITY));

        assertThatThrownBy(() -> commentService.createComment("-1", ""))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("he comment message cannot be empty or null");
    }

    @DisplayName("возвращать ожидаемый список комментарий для книги по ее id")
    @Test
    void shouldReturnExpectedCommentList() {
        when(commentRepository.findByBookId(any(String.class)))
                .thenReturn(List.of(COMMENT_ENTITY));
        when(commentMapper.entityToDto(List.of(COMMENT_ENTITY)))
                .thenReturn(List.of(EXPECTED_COMMENT_WITHOUT_BOOK_DTO));

        var actualList = commentService.getAllCommentsByBookId(COMMENT_ENTITY.getId());

        assertThat(actualList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(EXPECTED_COMMENT_WITHOUT_BOOK_DTO);
    }

    @DisplayName("возвращать ожидаемомый комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        when(commentRepository.findById(COMMENT_ENTITY.getId()))
                .thenReturn(Optional.of(COMMENT_ENTITY));

        when(commentMapper.entityToDto(any(Comment.class)))
                .thenReturn(EXPECTED_COMMENT_DTO);

        var actualComment = commentService.getCommentById(COMMENT_ENTITY.getId());

        assertThat(actualComment)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }

    @DisplayName("добавлять комментарий")
    @Test
    void shouldCreateComment() {
        when(commentRepository.save(any(Comment.class)))
                .thenReturn(COMMENT_ENTITY);
        when(commentMapper.entityToDto(any(Comment.class)))
                .thenReturn(EXPECTED_COMMENT_DTO);
        when(bookRepository.findById(any(String.class)))
                .thenReturn(Optional.of(BOOK_ENTITY));

        var actualComment = commentService.createComment(
                COMMENT_ENTITY.getBook().getId(),
                COMMENT_ENTITY.getMessageComment()
        );

        assertThat(actualComment)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }


    @DisplayName("обновлять коментарий по id")
    @Test
    void shouldUpdateComment() {
        when(commentRepository.save(any(Comment.class)))
                .thenReturn(COMMENT_ENTITY);
        when(commentMapper.entityToDto(any(Comment.class)))
                .thenReturn(EXPECTED_COMMENT_DTO);
        when(commentRepository.findById(COMMENT_ENTITY.getId()))
                .thenReturn(Optional.of(COMMENT_ENTITY));

        var actualComment = commentService.updateComment(
                COMMENT_ENTITY.getId(),
                COMMENT_ENTITY.getMessageComment()
        );

        assertThat(actualComment)
                .isEqualTo(EXPECTED_COMMENT_DTO);
    }


    @Test
    @DisplayName("корректно кидать исключение при попытке удалить несуществующий комментарий")
    void shouldCorrectlyExceptionByDeleteComment() {
        when(commentRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.deleteCommentById("-1"))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Comment not find with id = " + "-1");
    }
}
