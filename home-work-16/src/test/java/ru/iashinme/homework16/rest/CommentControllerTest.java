package ru.iashinme.homework16.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.iashinme.homework16.dto.BookDto;
import ru.iashinme.homework16.dto.CommentDto;
import ru.iashinme.homework16.dto.CommentSmallDto;
import ru.iashinme.homework16.dto.CommentWithoutBookDto;
import ru.iashinme.homework16.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@DisplayName("Rest контроллер для комментариев должен ")
public class CommentControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    private final static CommentWithoutBookDto EXPECTED_COMMENT_WITHOUT_BOOK_DTO = CommentWithoutBookDto
            .builder()
            .id(-1)
            .messageComment("text")
            .time(LocalDateTime.now())
            .build();

    private final static CommentDto EXPECTED_COMMENT_DTO = CommentDto
            .builder()
            .id(-1)
            .messageComment("text")
            .time(LocalDateTime.now())
            .book(BookDto.builder().id(-1).build())
            .build();

    private final static CommentSmallDto COMMENT_SMALL_DTO
            = new CommentSmallDto(0, -1, "text");

    @Test
    @DisplayName("корректно возвращать список коментариве по id книги")
    void shouldReturnCorrectCommentsListByBookId() throws Exception {
        when(commentService.findAllByBookId(any(Long.class))).thenReturn(List.of(EXPECTED_COMMENT_WITHOUT_BOOK_DTO));


        mvc.perform(get("/api/v1/book/-1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(EXPECTED_COMMENT_WITHOUT_BOOK_DTO))));
    }

    @Test
    @DisplayName("добавлять комментарий к книге")
    void shouldAddCorrectCommentForBook() throws Exception {
        when(commentService.create(
                EXPECTED_COMMENT_DTO.getBook().getId(),
                EXPECTED_COMMENT_DTO.getMessageComment()
        )).thenReturn(EXPECTED_COMMENT_DTO);

        var content = objectMapper.writeValueAsString(COMMENT_SMALL_DTO);

        mvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EXPECTED_COMMENT_DTO)));
    }

    @Test
    @DisplayName("корректно удалять комментарий по его id")
    void shouldDeleteCorrectCommentById() throws Exception {
        mvc.perform(delete("/api/v1/comment/" + EXPECTED_COMMENT_DTO.getBook().getId()))
                .andExpect(status().isOk());
    }
}
