package ru.iashinme.homework11.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.iashinme.homework11.dto.AuthorDto;
import ru.iashinme.homework11.dto.BookDto;
import ru.iashinme.homework11.dto.CommentDto;
import ru.iashinme.homework11.dto.GenreDto;
import ru.iashinme.homework11.mapper.CommentMapper;
import ru.iashinme.homework11.model.Author;
import ru.iashinme.homework11.model.Book;
import ru.iashinme.homework11.model.Comment;
import ru.iashinme.homework11.model.Genre;
import ru.iashinme.homework11.repository.CommentRepository;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {CommentController.class})
@DisplayName("Контроллер для работы с комментариями должен ")
public class CommentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private CommentMapper commentMapper;

    private final static Book EXPECTED_BOOK = new Book(
            "-1",
            "name",
            new Genre("-2", "genre"),
            new Author("-3", "surname", "name", "patron")
    );
    private final static BookDto EXPECTED_BOOK_DTO = BookDto.builder()
            .id(EXPECTED_BOOK.getId())
            .name(EXPECTED_BOOK.getName())
            .genre(GenreDto.builder()
                    .id(EXPECTED_BOOK.getGenre().getId())
                    .name(EXPECTED_BOOK.getGenre().getName())
                    .build())
            .author(AuthorDto.builder()
                    .id(EXPECTED_BOOK.getAuthor().getId())
                    .surname(EXPECTED_BOOK.getAuthor().getSurname())
                    .name(EXPECTED_BOOK.getAuthor().getName())
                    .patronymic(EXPECTED_BOOK.getAuthor().getPatronymic())
                    .build()
            )
            .build();

    private final static Comment COMMENT_ENTITY = new Comment("-1", EXPECTED_BOOK, "comment");
    private final static CommentDto EXPECTED_COMMENT_DTO = CommentDto
            .builder()
            .id(COMMENT_ENTITY.getId())
            .bookId(EXPECTED_BOOK_DTO.getId())
            .time(COMMENT_ENTITY.getTime())
            .messageComment(COMMENT_ENTITY.getMessageComment())
            .build();

    @Test
    @DisplayName("корректно возвращать список комментарий по id книги")
    public void getAllCommentsByBookId() {
        Flux<Comment> fluxFromStream = Flux.fromStream(Stream.of(COMMENT_ENTITY));
        when(commentRepository.findByBookId(EXPECTED_BOOK.getId())).thenReturn(fluxFromStream);
        when(commentMapper.entityToDto(any())).thenReturn(EXPECTED_COMMENT_DTO);

        webTestClient.get()
                .uri("/api/v1/book/" + EXPECTED_BOOK.getId() + "/comments")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
