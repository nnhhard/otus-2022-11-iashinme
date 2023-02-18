package ru.iashinme.homework11.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.iashinme.homework11.dto.GenreDto;
import ru.iashinme.homework11.mapper.GenreMapper;
import ru.iashinme.homework11.model.Genre;
import ru.iashinme.homework11.repository.GenreRepository;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {GenreController.class})
@DisplayName("Контроллер для работы с жанрами должен ")
public class GenreControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private GenreMapper genreMapper;

    private static final Genre EXPECTED_GENRE = new Genre("-1", "Сказка");
    private static final GenreDto EXPECTED_GENRE_DTO = GenreDto
            .builder()
            .id(EXPECTED_GENRE.getId())
            .name(EXPECTED_GENRE.getName())
            .build();

    @Test
    @DisplayName("корректно возвращать список жанров")
    public void getAllGenres() {
        Flux<Genre> fluxFromStream = Flux.fromStream(Stream.of(EXPECTED_GENRE));
        when(genreRepository.findAll()).thenReturn(fluxFromStream);
        when(genreMapper.entityToDto(any())).thenReturn(EXPECTED_GENRE_DTO);

        webTestClient.get()
                .uri("/api/v1/genre")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
