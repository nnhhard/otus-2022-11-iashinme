package ru.iashinme.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.iashinme.blog.config.SecurityConfig;
import ru.iashinme.blog.dto.AuthDto;
import ru.iashinme.blog.dto.RegistrationDto;
import ru.iashinme.blog.dto.TechnologyDto;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.rest.AuthController;
import ru.iashinme.blog.rest.PostController;
import ru.iashinme.blog.security.JwtConfigurer;
import ru.iashinme.blog.security.JwtTokenFilter;
import ru.iashinme.blog.security.JwtTokenProvider;
import ru.iashinme.blog.service.PostService;
import ru.iashinme.blog.service.UserService;

import javax.servlet.http.Cookie;
import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ContextConfiguration(classes = {JwtConfigurer.class, JwtTokenFilter.class, SecurityConfig.class})
@Import(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserService userService;

    @ParameterizedTest(name = "Non authorized {0}")
    @MethodSource("generateHttpServletRequestBuilder")
    void shouldNotAuthorizedStatusForRequestsWithoutAuthorized(String path, MockHttpServletRequestBuilder builder, ResultMatcher checkStatus) throws Exception {
        mockMvc.perform(builder)
                .andExpect(checkStatus);
    }

    @ParameterizedTest(name = "User {0}")
    @MethodSource("generateHttpServletRequestBuilderWithUser")
    void shouldNotAuthorizedStatusForRequestsWithUser(String path, MockHttpServletRequestBuilder builder, ResultMatcher checkStatus) throws Exception {
        UserDetails userDetails = new User(1L, "user", "", "user", "test@test.ru", true, true, true, true, Set.of(new Authority(1L, "ROLE_USER")));

        given(jwtTokenProvider.getUsername("asdlkjadshfklsdfjals")).willReturn("user");
        given(userDetailsService.loadUserByUsername("user")).willReturn(userDetails);

        mockMvc.perform(builder)
                .andExpect(checkStatus);
    }

    private static Stream<Arguments> generateHttpServletRequestBuilderWithUser() throws JsonProcessingException {
        Cookie cookie = new Cookie("userToken", "asdlkjadshfklsdfjals");
        cookie.setPath("/");
        cookie.setMaxAge(365 * 24 * 60 * 60);

        ObjectMapper mapper = new ObjectMapper();

        return Stream.of(
                Arguments.of("post /api/v1/login", post("/api/v1/login")
                        .content(mapper.writeValueAsBytes(new AuthDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE), status().isOk()),
                Arguments.of("post /api/v1/registration", post("/api/v1/registration")
                        .content(mapper.writeValueAsBytes(new RegistrationDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE), status().isOk()),
                Arguments.of("delete /api/v1/logout", delete("/api/v1/logout")
                        .cookie(cookie), status().isForbidden())
        );
    }

    private static Stream<Arguments> generateHttpServletRequestBuilder() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        return Stream.of(
                Arguments.of("post /api/v1/login", post("/api/v1/login")
                        .content(mapper.writeValueAsBytes(new AuthDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE), status().isOk()),
                Arguments.of("post /api/v1/registration", post("/api/v1/registration")
                        .content(mapper.writeValueAsBytes(new RegistrationDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE), status().isOk()),
                Arguments.of("delete /api/v1/logout", delete("/api/v1/logout"), status().isForbidden())
        );
    }
}
