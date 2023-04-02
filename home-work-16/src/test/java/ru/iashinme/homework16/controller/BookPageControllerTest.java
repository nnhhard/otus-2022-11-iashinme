package ru.iashinme.homework16.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.iashinme.homework16.dto.AuthorDto;
import ru.iashinme.homework16.dto.BookDto;
import ru.iashinme.homework16.dto.GenreDto;
import ru.iashinme.homework16.model.Author;
import ru.iashinme.homework16.model.Book;
import ru.iashinme.homework16.model.Genre;
import ru.iashinme.homework16.service.AuthorService;
import ru.iashinme.homework16.service.BookService;
import ru.iashinme.homework16.service.GenreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookPageController.class)
public class BookPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private static final Genre EXPECTED_GENRE = new Genre(-1, "GENRE_NAME");
    private static final GenreDto EXPECTED_GENRE_DTO = GenreDto
            .builder()
            .id(EXPECTED_GENRE.getId())
            .name(EXPECTED_GENRE.getName())
            .build();


    private static final Author EXPECTED_AUTHOR = new Author(-1, "surname", "name", "patronymic");
    private static final AuthorDto EXPECTED_AUTHOR_DTO = AuthorDto
            .builder()
            .id(EXPECTED_AUTHOR.getId())
            .surname(EXPECTED_AUTHOR.getSurname())
            .name(EXPECTED_AUTHOR.getName())
            .patronymic(EXPECTED_AUTHOR.getPatronymic())
            .build();


    private static final Book EXPECTED_BOOK = new Book(-1, "Мертвые дущи том №2", EXPECTED_AUTHOR, EXPECTED_GENRE);
    private static final BookDto EXPECTED_BOOK_DTO = BookDto
            .builder()
            .id(EXPECTED_BOOK.getId())
            .name(EXPECTED_BOOK.getName())
            .genre(EXPECTED_GENRE_DTO)
            .author(EXPECTED_AUTHOR_DTO)
            .build();

    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        when(bookService.findAll()).thenReturn(List.of(EXPECTED_BOOK_DTO));

        mvc.perform(get("/books")).andExpect(status().isOk());
    }

    @Test
    void shouldReturnEditBook() throws Exception {
        when(bookService.findById(any(Long.class))).thenReturn(EXPECTED_BOOK_DTO);
        when(authorService.findAll()).thenReturn(List.of(EXPECTED_AUTHOR_DTO));
        when(genreService.findAll()).thenReturn(List.of(EXPECTED_GENRE_DTO));

        mvc.perform(get("/books/edit").param("id", "1"))
                .andExpect(status().isOk());
    }
}
