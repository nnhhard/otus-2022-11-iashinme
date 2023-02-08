package ru.iashinme.homework09.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework09.dto.*;
import ru.iashinme.homework09.exception.ValidateException;
import ru.iashinme.homework09.mapper.CommentMapper;
import ru.iashinme.homework09.mapper.CommentWithoutBookMapper;
import ru.iashinme.homework09.model.Author;
import ru.iashinme.homework09.model.Book;
import ru.iashinme.homework09.model.Comment;
import ru.iashinme.homework09.model.Genre;
import ru.iashinme.homework09.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с комменатриями должен ")
@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceImplTest {

    private static final Author AUTHOR_ENTITY = new Author(-1, "surname", "name", "patronymic");
    private static final AuthorDto AUTHOR_DTO = AuthorDto
            .builder()
            .id(AUTHOR_ENTITY.getId())
            .surname(AUTHOR_ENTITY.getSurname())
            .name(AUTHOR_ENTITY.getName())
            .patronymic(AUTHOR_ENTITY.getPatronymic())
            .build();
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

    private final static BookDto BOOK_WITH_ALL_INFO_DTO = BookDto
            .builder()
            .id(BOOK_ENTITY.getId())
            .name(BOOK_ENTITY.getName())
            .genre(GENRE_DTO)
            .author(AUTHOR_DTO)
            .build();

    private final static Comment COMMENT_ENTITY = new Comment(-1, BOOK_ENTITY, "comment");
    private final static CommentDto EXPECTED_COMMENT_DTO = CommentDto
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
    private CommentMapper commentMapper;

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки сообщения комментария")
    public void shouldHaveCorrectExceptionForCheckMessageComment() {
        when(bookService.findById(any(Long.class))).thenReturn(
                BookDto.builder()
                        .id(-1)
                        .name("name")
                        .build()
        );

        assertThatThrownBy(() -> commentService.create(-1, ""))
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

        var actualList = commentService.findAllByBookId(COMMENT_ENTITY.getId());

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

        var actualComment = commentService.findById(COMMENT_ENTITY.getId());

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
        when(bookService.findById(any(Long.class)))
                .thenReturn(BOOK_WITH_ALL_INFO_DTO);

        var actualComment = commentService.create(
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

        var actualComment = commentService.update(
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

        assertThatThrownBy(() -> commentService.deleteById(-1))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Comment not find with id = " + -1);
    }
}
