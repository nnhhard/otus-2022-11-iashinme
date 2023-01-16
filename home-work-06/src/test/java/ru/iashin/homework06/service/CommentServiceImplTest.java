package ru.iashin.homework06.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashin.homework06.dto.*;
import ru.iashin.homework06.exception.ValidateException;
import ru.iashin.homework06.mapper.CommentWithBookIdNameGenreMapper;
import ru.iashin.homework06.mapper.CommentWithoutBookMapper;
import ru.iashin.homework06.model.Book;
import ru.iashin.homework06.model.Comment;
import ru.iashin.homework06.model.Genre;
import ru.iashin.homework06.repository.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с комменатриями должен ")
@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceImplTest {

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

    private final static BookWithAllInfoDto BOOK_WITH_ALL_INFO_DTO = BookWithAllInfoDto
            .builder()
            .id(BOOK_ENTITY.getId())
            .name(BOOK_ENTITY.getName())
            .genre(GENRE_DTO)
            .authors(Collections.emptyList())
            .build();

    private final static Comment COMMENT_ENTITY = new Comment(-1, BOOK_ENTITY, "comment");
    private final static CommentWithBookIdNameGenreDto EXPECTED_COMMENT_DTO = CommentWithBookIdNameGenreDto
            .builder()
            .id(COMMENT_ENTITY.getId())
            .book(BOOK_DTO)
            .time(COMMENT_ENTITY.getTime())
            .messageComment(COMMENT_ENTITY.getMessageComment())
            .build();

    private final static CommentWithoutBookDto EXPECTED_COMMENT_WITHOUT_BOOK_DTO = CommentWithoutBookDto
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
    private BookService bookService;

    @MockBean
    private CommentWithoutBookMapper commentWithoutBookMapper;

    @MockBean
    private CommentWithBookIdNameGenreMapper commentWithBookIdNameGenreMapper;

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
        when(commentRepository.findByBookId(any(Long.class)))
                .thenReturn(List.of(COMMENT_ENTITY));
        when(commentWithoutBookMapper.entityToDto(List.of(COMMENT_ENTITY)))
                .thenReturn(List.of(EXPECTED_COMMENT_WITHOUT_BOOK_DTO));

        var actualList = commentService.getAllCommentsByBookId(COMMENT_ENTITY.getId());

        assertThat(actualList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(EXPECTED_COMMENT_WITHOUT_BOOK_DTO);
    }

    @DisplayName("возвращать ожидаемомый комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        when(commentRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(COMMENT_ENTITY));
        when(commentWithBookIdNameGenreMapper.entityToDto(any(Comment.class)))
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
        when(commentWithBookIdNameGenreMapper.entityToDto(any(Comment.class)))
                .thenReturn(EXPECTED_COMMENT_DTO);
        when(bookService.getBookById(any(Long.class)))
                .thenReturn(BOOK_WITH_ALL_INFO_DTO);

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
        when(commentWithBookIdNameGenreMapper.entityToDto(any(Comment.class)))
                .thenReturn(EXPECTED_COMMENT_DTO);
        when(commentRepository.findById(any(Long.class)))
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
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.deleteCommentById(-1))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Comment not find with id = " + -1);
    }
}
