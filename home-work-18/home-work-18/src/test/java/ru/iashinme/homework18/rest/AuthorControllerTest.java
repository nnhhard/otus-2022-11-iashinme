package ru.iashinme.homework18.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.iashinme.homework18.dto.AuthorDto;
import ru.iashinme.homework18.service.AuthorService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@DisplayName("Rest контроллер для Авторов должен ")
public class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    private final static AuthorDto EXPECTED_AUTHOR_DTO = AuthorDto
            .builder()
            .id(-1)
            .surname("surname1")
            .name("name1")
            .patronymic("patronymic1")
            .build();

    @Test
    @DisplayName("корректно возвращать список авторов")
    void shouldReturnCorrectAuthorsList() throws Exception {
        when(authorService.findAll()).thenReturn(List.of(EXPECTED_AUTHOR_DTO));

        mvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(EXPECTED_AUTHOR_DTO))));
    }

    @Test
    @DisplayName("корректно возвращать автора по его id")
    void shouldReturnCorrectAuthorById() throws Exception {
        when(authorService.findById(EXPECTED_AUTHOR_DTO.getId())).thenReturn(EXPECTED_AUTHOR_DTO);

        mvc.perform(get("/api/v1/author/" + EXPECTED_AUTHOR_DTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EXPECTED_AUTHOR_DTO)));
    }

    @Test
    @DisplayName("корректно удалять автора по его id")
    void shouldDeleteCorrectAuthorById() throws Exception {
        mvc.perform(delete("/api/v1/author/" + EXPECTED_AUTHOR_DTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("корректно создавать автора")
    void shouldCreateCorrectAuthor() throws Exception {
        when(authorService.create(
                EXPECTED_AUTHOR_DTO.getSurname(),
                EXPECTED_AUTHOR_DTO.getName(),
                EXPECTED_AUTHOR_DTO.getPatronymic()
        )).thenReturn(EXPECTED_AUTHOR_DTO);

        String content = objectMapper.writeValueAsString(EXPECTED_AUTHOR_DTO);
        mvc.perform(post("/api/v1/author")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

    @Test
    @DisplayName("корректно обновлять автора")
    void shouldUpdateCorrectAuthor() throws Exception {
        when(authorService.update(
                EXPECTED_AUTHOR_DTO.getId(),
                EXPECTED_AUTHOR_DTO.getSurname(),
                EXPECTED_AUTHOR_DTO.getName(),
                EXPECTED_AUTHOR_DTO.getPatronymic()
        )).thenReturn(EXPECTED_AUTHOR_DTO);

        String content = objectMapper.writeValueAsString(EXPECTED_AUTHOR_DTO);
        mvc.perform(put("/api/v1/author")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }
}
