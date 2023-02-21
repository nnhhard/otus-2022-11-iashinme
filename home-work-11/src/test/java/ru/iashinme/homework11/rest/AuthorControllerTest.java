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
import ru.iashinme.homework11.advice.GlobalExceptionHandler;
import ru.iashinme.homework11.dto.AuthorDto;
import ru.iashinme.homework11.mapper.AuthorMapper;
import ru.iashinme.homework11.model.Author;
import ru.iashinme.homework11.repository.AuthorRepository;
import ru.iashinme.homework11.repository.BookRepository;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {AuthorController.class, GlobalExceptionHandler.class})
@DisplayName(" Контроллер для работы с Авторами должен ")
public class AuthorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private AuthorMapper authorMapper;

    @MockBean
    private BookRepository bookRepository;

    private static final Author EXPECTED_AUTHOR = new Author("-1", "surname", "name", "patronymic");
    private static final AuthorDto EXPECTED_AUTHOR_DTO = AuthorDto.builder()
            .id(EXPECTED_AUTHOR.getId())
            .surname(EXPECTED_AUTHOR.getSurname())
            .name(EXPECTED_AUTHOR.getName())
            .patronymic(EXPECTED_AUTHOR.getPatronymic())
            .build();

    @Test
    @DisplayName("возвращать список авторов")
    public void getAllAuthors() {
        Flux<Author> fluxFromStream = Flux.fromStream(Stream.of(EXPECTED_AUTHOR));
        when(authorRepository.findAll()).thenReturn(fluxFromStream);
        when(authorMapper.entityToDto(any())).thenReturn(EXPECTED_AUTHOR_DTO);

        webTestClient.get()
                .uri("/api/v1/author")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("возвращать автора по его id")
    public void getAuthorById() {
        Mono<Author> monoFromStream = Mono.just(EXPECTED_AUTHOR);
        when(authorRepository.findById(EXPECTED_AUTHOR.getId())).thenReturn(monoFromStream);
        when(authorMapper.entityToDto(any())).thenReturn(EXPECTED_AUTHOR_DTO);

        webTestClient.get()
                .uri("/api/v1/author/" + EXPECTED_AUTHOR.getId())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("удалять автора по его id")
    public void deleteAuthorById() {
        when(bookRepository.existsBookByAuthor_Id((EXPECTED_AUTHOR.getId()))).thenReturn(Mono.just(false));
        when(authorRepository.deleteById(EXPECTED_AUTHOR.getId())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/author/" + EXPECTED_AUTHOR.getId())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("не удалять автора, если существую книги с данным автором")
    public void noDeleteAuthorById() {
        when(bookRepository.existsBookByAuthor_Id((EXPECTED_AUTHOR.getId()))).thenReturn(Mono.just(Boolean.TRUE));

        webTestClient.delete()
                .uri("/api/v1/author/" + EXPECTED_AUTHOR.getId())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();
    }

    @Test
    @DisplayName("создавать автора")
    public void addAuthorById() {
        Mono<Author> monoFromStream = Mono.just(EXPECTED_AUTHOR);
        when(authorRepository.save(any())).thenReturn(monoFromStream);
        when(authorMapper.entityToDto(any())).thenReturn(EXPECTED_AUTHOR_DTO);

        webTestClient.post()
                .uri("/api/v1/author")
                .bodyValue(EXPECTED_AUTHOR_DTO)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("обновлять автора")
    public void updateAuthorById() {
        when(authorRepository.existsById(any(String.class))).thenReturn(Mono.just(true));
        when(authorRepository.save(any())).thenReturn(Mono.empty());
        when(bookRepository.updateAuthorInBook(any())).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/api/v1/author")
                .bodyValue(EXPECTED_AUTHOR_DTO)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("не обновлять не найденного автора")
    public void noUpdateAuthorById() {
        when(authorRepository.existsById(any(String.class))).thenReturn(Mono.just(Boolean.FALSE));

        webTestClient.put()
                .uri("/api/v1/author")
                .bodyValue(EXPECTED_AUTHOR_DTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}
