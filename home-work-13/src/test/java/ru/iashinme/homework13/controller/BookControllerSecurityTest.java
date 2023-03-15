package ru.iashinme.homework13.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.iashinme.homework13.security.SecurityConfiguration;
import ru.iashinme.homework13.service.AuthorService;
import ru.iashinme.homework13.service.BookService;
import ru.iashinme.homework13.service.GenreService;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
public class BookControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsService userService;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @ParameterizedTest(name = "{0}")
    @MethodSource("generateHttpServletRequestBuilder")
    void shouldNotAuthorizedStatusForRequestsWithoutAuthorized(String path, MockHttpServletRequestBuilder builder, ResultMatcher checkStatus, ResultMatcher checkRedirectedUrl) throws Exception {
        mvc.perform(builder)
                .andExpect(checkStatus)
                .andExpect(checkRedirectedUrl);
    }

    private static Stream<Arguments> generateHttpServletRequestBuilder() {
        return Stream.of(
                Arguments.of("/books/edit", post("/books/edit").with(csrf()), status().is3xxRedirection(), redirectedUrl("http://localhost/login")),
                Arguments.of("/books/delete/1", post("/books/delete/1").with(csrf()), status().is3xxRedirection(), redirectedUrl("http://localhost/login")),
                Arguments.of("/books", get("/books"), status().is3xxRedirection(), redirectedUrl("http://localhost/login")),
                Arguments.of("/books/edit", get("/books/edit"), status().is3xxRedirection(), redirectedUrl("http://localhost/login"))
        );
    }

    @WithMockUser(value = "spring", roles = "USER")
    @ParameterizedTest(name = "{0}")
    @MethodSource("generateHttpServletRequestBuilderForbiddenWithUserRole")
    public void shouldReturnForbiddenWithUserRole(String path, MockHttpServletRequestBuilder builder, ResultMatcher checkStatus) throws Exception {
        mvc.perform(builder)
                .andExpect(checkStatus);
    }

    private static Stream<Arguments> generateHttpServletRequestBuilderForbiddenWithUserRole() {
        return Stream.of(
                Arguments.of("/books/edit", post("/books/edit").with(csrf()), status().isForbidden()),
                Arguments.of("/books/delete/1", post("/books/delete/1").with(csrf()), status().isForbidden()),
                Arguments.of("/books/edit", get("/books/edit"), status().isForbidden()),
                Arguments.of("/books", get("/books"), status().isOk())
        );
    }


}
