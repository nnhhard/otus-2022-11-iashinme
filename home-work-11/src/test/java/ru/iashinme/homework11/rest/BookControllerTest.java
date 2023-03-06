package ru.iashinme.homework11.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.dto.AuthorDto;
import ru.iashinme.homework11.dto.BookDto;
import ru.iashinme.homework11.dto.GenreDto;
import ru.iashinme.homework11.mapper.BookMapper;
import ru.iashinme.homework11.model.Author;
import ru.iashinme.homework11.model.Book;
import ru.iashinme.homework11.model.Genre;
import ru.iashinme.homework11.repository.BookRepository;
import ru.iashinme.homework11.repository.CommentRepository;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {BookController.class})
@DisplayName("Контроллер для работы с книгами должен ")
public class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookMapper bookMapper;

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

    @Test
    @DisplayName("корректно возвращать книгу по ее id")
    public void findById() {
        Mono<Book> monoFromStream = Mono.just(EXPECTED_BOOK);
        when(bookRepository.findById(EXPECTED_BOOK.getId())).thenReturn(monoFromStream);
        when(bookMapper.entityToDto(any())).thenReturn(EXPECTED_BOOK_DTO);

        webTestClient.get()
                .uri("/api/v1/book/" + EXPECTED_BOOK.getId())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("корректно возвращать список книг")
    public void findAllTest() {
        Flux<Book> fluxFromStream = Flux.fromStream(Stream.of(EXPECTED_BOOK));
        when(bookRepository.findAll()).thenReturn(fluxFromStream);
        when(bookMapper.entityToDto(any())).thenReturn(EXPECTED_BOOK_DTO);

        webTestClient.get()
                .uri("/api/v1/book")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("корректно сохранять книгу")
    @Test
    public void saveBook() {
        Mono<Book> monoFromStream = Mono.just(EXPECTED_BOOK);
        when(bookRepository.save(any())).thenReturn(monoFromStream);
        when(bookMapper.entityToDto(EXPECTED_BOOK)).thenReturn(EXPECTED_BOOK_DTO);

        webTestClient.post()
                .uri("/api/v1/book")
                .bodyValue(EXPECTED_BOOK_DTO)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("корректно обновлять книгу")
    @Test
    public void updateBook() {
        Mono<Book> monoFromStream = Mono.just(EXPECTED_BOOK);
        when(bookRepository.save(any())).thenReturn(monoFromStream);
        when(bookMapper.entityToDto(EXPECTED_BOOK)).thenReturn(EXPECTED_BOOK_DTO);

        webTestClient.put()
                .uri("/api/v1/book")
                .bodyValue(EXPECTED_BOOK_DTO)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("корректно удалять книгу")
    @Test
    public void deleteBook() {
        when(bookRepository.deleteById(any(String.class))).thenReturn(Mono.empty());
        when(commentRepository.deleteByBook_Id(any(String.class))).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/book/1")
                .exchange()
                .expectStatus()
                .isOk();
    }
}