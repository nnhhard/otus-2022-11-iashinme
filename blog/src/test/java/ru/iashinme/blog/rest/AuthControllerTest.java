package ru.iashinme.blog.rest;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.iashinme.blog.config.SecurityConfig;
import ru.iashinme.blog.dto.AuthDto;
import ru.iashinme.blog.dto.CustomUserDetails;
import ru.iashinme.blog.dto.RegistrationDto;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.security.JwtConfigurer;
import ru.iashinme.blog.security.JwtTokenFilter;
import ru.iashinme.blog.security.JwtTokenProvider;
import ru.iashinme.blog.service.UserService;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @MethodSource("generateData")
    void shouldCorrectStatusForRequestsWithoutAuthorized(String path, MockHttpServletRequestBuilder builder, ResultMatcher checkStatus) throws Exception {
        mockMvc.perform(builder)
                .andExpect(checkStatus);
    }

    @ParameterizedTest(name = "User {0}")
    @MethodSource("generateDataForUser")
    void shouldCorrectStatusForRequestsWithAuthorizedUser(String path, MockHttpServletRequestBuilder builder, ResultMatcher checkStatus) throws Exception {
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .id(-1L)
                .username("user")
                .password("")
                .fullName("user")
                .email("test@test.ru")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .authorities(List.of(new Authority(-1L, "ROLE_USER")))
                .build();

        when(jwtTokenProvider.getUsername("asdlkjadshfklsdfjals")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);

        mockMvc.perform(builder)
                .andExpect(checkStatus);
    }

    private static Stream<Arguments> generateDataForUser() throws JsonProcessingException {
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
                        .cookie(cookie), status().isOk())
        );
    }

    private static Stream<Arguments> generateData() throws JsonProcessingException {

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
