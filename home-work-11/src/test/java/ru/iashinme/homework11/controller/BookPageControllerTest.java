package ru.iashinme.homework11.controller;

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
import ru.iashinme.homework11.mapper.AuthorMapper;
import ru.iashinme.homework11.mapper.BookMapper;
import ru.iashinme.homework11.mapper.GenreMapper;
import ru.iashinme.homework11.model.Author;
import ru.iashinme.homework11.model.Book;
import ru.iashinme.homework11.model.Genre;
import ru.iashinme.homework11.repository.AuthorRepository;
import ru.iashinme.homework11.repository.BookRepository;
import ru.iashinme.homework11.repository.GenreRepository;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebFluxTest
@ContextConfiguration(classes = {BookPageController.class})
public class BookPageControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private GenreMapper genreMapper;

    @MockBean
    private AuthorMapper authorMapper;

    private static final Genre EXPECTED_GENRE = new Genre("-1", "GENRE_NAME");
    private static final GenreDto EXPECTED_GENRE_DTO = GenreDto
            .builder()
            .id(EXPECTED_GENRE.getId())
            .name(EXPECTED_GENRE.getName())
            .build();


    private static final Author EXPECTED_AUTHOR = new Author("-1", "surname", "name", "patronymic");
    private static final AuthorDto EXPECTED_AUTHOR_DTO = AuthorDto
            .builder()
            .id(EXPECTED_AUTHOR.getId())
            .surname(EXPECTED_AUTHOR.getSurname())
            .name(EXPECTED_AUTHOR.getName())
            .patronymic(EXPECTED_AUTHOR.getPatronymic())
            .build();


    private static final Book EXPECTED_BOOK = new Book("-1", "Мертвые дущи том №2", EXPECTED_AUTHOR, EXPECTED_GENRE);
    private static final BookDto EXPECTED_BOOK_DTO = BookDto
            .builder()
            .id(EXPECTED_BOOK.getId())
            .name(EXPECTED_BOOK.getName())
            .genre(EXPECTED_GENRE_DTO)
            .author(EXPECTED_AUTHOR_DTO)
            .build();

    @Test
    void shouldReturnCorrectBooksList() {
        webTestClient.get()
                .uri("/books")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void shouldReturnEditBook() {
        when(bookRepository.findById(any(String.class))).thenReturn(Mono.just(EXPECTED_BOOK));
        when(authorRepository.findAll()).thenReturn(Flux.fromStream(Stream.of(EXPECTED_AUTHOR)));
        when(genreRepository.findAll()).thenReturn(Flux.fromStream(Stream.of(EXPECTED_GENRE)));
        when(bookMapper.entityToDto(any())).thenReturn(EXPECTED_BOOK_DTO);
        when(authorMapper.entityToDto(any())).thenReturn(EXPECTED_AUTHOR_DTO);
        when(genreMapper.entityToDto(any())).thenReturn(EXPECTED_GENRE_DTO);

        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/books/edit")
                                .queryParam("id", "-1")
                                .build())
                .exchange()
                .expectStatus()
                .isOk();
    }
}
