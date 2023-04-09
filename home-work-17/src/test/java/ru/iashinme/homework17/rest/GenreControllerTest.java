package ru.iashinme.homework17.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.iashinme.homework17.dto.GenreDto;
import ru.iashinme.homework17.service.GenreService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@DisplayName("Rest контроллер для жанров должен ")
public class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreService genreService;

    private final static GenreDto EXPECTED_GENRE_DTO = GenreDto
            .builder()
            .id(-1)
            .name("name1")
            .build();

    @Test
    @DisplayName("корректно возвращать список жанров")
    void shouldReturnCorrectGenresList() throws Exception {
        when(genreService.findAll()).thenReturn(List.of(EXPECTED_GENRE_DTO));

        mvc.perform(get("/api/v1/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(EXPECTED_GENRE_DTO))));
    }

    @Test
    @DisplayName("корректно возвращать жанр по его id")
    void shouldReturnCorrectGenreById() throws Exception {
        when(genreService.findById(EXPECTED_GENRE_DTO.getId())).thenReturn(EXPECTED_GENRE_DTO);

        mvc.perform(get("/api/v1/genre/" + EXPECTED_GENRE_DTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EXPECTED_GENRE_DTO)));
    }

    @Test
    @DisplayName("корректно удалять жанр по его id")
    void shouldDeleteCorrectGenreById() throws Exception {
        mvc.perform(delete("/api/v1/genre/" + EXPECTED_GENRE_DTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("корректно создавать жанр")
    void shouldCreateCorrectGenre() throws Exception {
        when(genreService.create(EXPECTED_GENRE_DTO.getName())).thenReturn(EXPECTED_GENRE_DTO);

        String content = objectMapper.writeValueAsString(EXPECTED_GENRE_DTO);
        mvc.perform(post("/api/v1/genre")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

    @Test
    @DisplayName("корректно обновлять жанр")
    void shouldUpdateCorrectGenre() throws Exception {
        when(genreService.update(
                EXPECTED_GENRE_DTO.getId(),
                EXPECTED_GENRE_DTO.getName()
        )).thenReturn(EXPECTED_GENRE_DTO);

        String content = objectMapper.writeValueAsString(EXPECTED_GENRE_DTO);
        mvc.perform(put("/api/v1/genre")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

}
