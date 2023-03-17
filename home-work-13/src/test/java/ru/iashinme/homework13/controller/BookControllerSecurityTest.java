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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
public class BookControllerSecurityTest {

    private final static String URL_BOOK_EDIT = "/books/edit";
    private final static String URL_BOOK_DELETE = "/books/delete/1";
    private final static String URL_BOOKS = "/books";

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
    void shouldCorrectStatusForRequestsWithDifferentUsers(String path, MockHttpServletRequestBuilder builder, ResultMatcher... matchers) throws Exception {
        mvc.perform(builder)
                .andExpectAll(matchers);
    }

    private static Stream<Arguments> generateHttpServletRequestBuilder() {
        var user = user("user").roles("USER");
        var admin = user("user").roles("ADMIN");

        return Stream.of(
                Arguments.of(URL_BOOK_EDIT, post(URL_BOOK_EDIT).with(csrf()), new ResultMatcher[]{status().is3xxRedirection(), redirectedUrl("http://localhost/login")}),
                Arguments.of(URL_BOOK_EDIT, post(URL_BOOK_EDIT).with(csrf()).with(user), new ResultMatcher[]{status().isForbidden()}),
                Arguments.of(URL_BOOK_EDIT, post(URL_BOOK_EDIT).with(csrf()).with(admin), new ResultMatcher[]{status().is3xxRedirection(), redirectedUrl("/books")}),

                Arguments.of(URL_BOOK_EDIT, get(URL_BOOK_EDIT), new ResultMatcher[]{status().is3xxRedirection(), redirectedUrl("http://localhost/login")}),
                Arguments.of(URL_BOOK_EDIT, get(URL_BOOK_EDIT).with(user), new ResultMatcher[]{status().isForbidden()}),
                Arguments.of(URL_BOOK_EDIT, get(URL_BOOK_EDIT).param("id", "-1").with(admin), new ResultMatcher[]{status().isOk()}),

                Arguments.of(URL_BOOKS, get(URL_BOOKS), new ResultMatcher[]{status().is3xxRedirection(), redirectedUrl("http://localhost/login")}),
                Arguments.of(URL_BOOKS, get(URL_BOOKS).with(user), new ResultMatcher[]{status().isOk()}),
                Arguments.of(URL_BOOKS, get(URL_BOOKS).with(admin), new ResultMatcher[]{status().isOk()}),

                Arguments.of(URL_BOOK_DELETE, post(URL_BOOK_DELETE).with(csrf()), new ResultMatcher[]{status().is3xxRedirection(), redirectedUrl("http://localhost/login")}),
                Arguments.of(URL_BOOK_DELETE, post(URL_BOOK_DELETE).with(csrf()).with(user), new ResultMatcher[]{status().isForbidden()}),
                Arguments.of(URL_BOOK_DELETE, post(URL_BOOK_DELETE).with(csrf()).with(admin), new ResultMatcher[]{status().is3xxRedirection(), redirectedUrl("/books")})
        );
    }
}
