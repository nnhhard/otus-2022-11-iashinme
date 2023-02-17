package ru.iashinme.homework10.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.iashinme.homework10.dto.AuthorDto;
import ru.iashinme.homework10.dto.BookDto;
import ru.iashinme.homework10.dto.BookSmallDto;
import ru.iashinme.homework10.dto.GenreDto;
import ru.iashinme.homework10.service.BookService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@DisplayName("Rest контроллер для книг должен ")
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private final static BookDto EXPECTED_BOOK_DTO = BookDto
            .builder()
            .id(-1)
            .name("book name")
            .genre(GenreDto.builder().id(-2).name("genre name").build())
            .author(AuthorDto.builder().id(-3).surname("surname").name("name").patronymic("patronymic").build())
            .build();

    private final static BookSmallDto BOOK_SMALL_DTO = new BookSmallDto(
            EXPECTED_BOOK_DTO.getId(),
            EXPECTED_BOOK_DTO.getName(),
            EXPECTED_BOOK_DTO.getAuthor().getId(),
            EXPECTED_BOOK_DTO.getGenre().getId()
    );

    @Test
    @DisplayName("корректно возвращать список книг")
    void shouldReturnCorrectBooksList() throws Exception {
        when(bookService.findAll()).thenReturn(List.of(EXPECTED_BOOK_DTO));

        mvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(EXPECTED_BOOK_DTO))));
    }

    @Test
    @DisplayName("корректно возвращать книгу по ее id")
    void shouldReturnCorrectBookById() throws Exception {
        when(bookService.findById(EXPECTED_BOOK_DTO.getId())).thenReturn(EXPECTED_BOOK_DTO);

        mvc.perform(get("/api/v1/book/" + EXPECTED_BOOK_DTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EXPECTED_BOOK_DTO)));
    }

    @Test
    @DisplayName("корректно удалять книгу по ее id")
    void shouldDeleteCorrectBookById() throws Exception {
        mvc.perform(delete("/api/v1/book/" + EXPECTED_BOOK_DTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("корректно создавать книгу")
    void shouldCreateCorrectBook() throws Exception {
        when(bookService.create(
                EXPECTED_BOOK_DTO.getName(),
                EXPECTED_BOOK_DTO.getAuthor().getId(),
                EXPECTED_BOOK_DTO.getGenre().getId()
                )
        ).thenReturn(EXPECTED_BOOK_DTO);

        String content = objectMapper.writeValueAsString(BOOK_SMALL_DTO);
        mvc.perform(post("/api/v1/book")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EXPECTED_BOOK_DTO)));
    }

    @Test
    @DisplayName("корректно обновлять книгу")
    void shouldUpdateCorrectBook() throws Exception {
        when(bookService.update(
                EXPECTED_BOOK_DTO.getId(),
                EXPECTED_BOOK_DTO.getName(),
                EXPECTED_BOOK_DTO.getAuthor().getId(),
                EXPECTED_BOOK_DTO.getGenre().getId()
        )).thenReturn(EXPECTED_BOOK_DTO);

        String content = objectMapper.writeValueAsString(BOOK_SMALL_DTO);
        mvc.perform(put("/api/v1/book")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EXPECTED_BOOK_DTO)));
    }
}
