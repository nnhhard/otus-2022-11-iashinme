package ru.iashinme.blog.rest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.iashinme.blog.config.SecurityConfig;
import ru.iashinme.blog.dto.CustomUserDetails;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.security.JwtConfigurer;
import ru.iashinme.blog.security.JwtTokenFilter;
import ru.iashinme.blog.security.JwtTokenProvider;
import ru.iashinme.blog.service.SecurityService;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecurityController.class)
@ContextConfiguration(classes = {JwtConfigurer.class, JwtTokenFilter.class, SecurityConfig.class})
@Import(SecurityController.class)
public class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private SecurityService securityService;

    @ParameterizedTest(name = "Non authorized {0}")
    @MethodSource("generateData")
    void shouldCorrectStatusForRequestsWithoutAuthorized(String path, MockHttpServletRequestBuilder builder) throws Exception {
        mockMvc.perform(builder)
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest(name = "Security {0}")
    @MethodSource("generateDataForSecurity")
    void shouldCorrectStatusForRequestsWithAuthorizedSecurity(String path, MockHttpServletRequestBuilder builder, ResultMatcher checkStatus) throws Exception {
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .id(-1L)
                .username("security")
                .password("")
                .fullName("security")
                .email("test@test.ru")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .authorities(List.of(new Authority(-1L, "ROLE_SECURITY")))
                .build();

        when(jwtTokenProvider.getUsername("asdlkjadshfklsdfjals")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);

        mockMvc.perform(builder)
                .andExpect(checkStatus);
    }

    @ParameterizedTest(name = "Admin {0}")
    @MethodSource("generateDataForAdmin")
    void shouldCorrectStatusForRequestsWithAuthorizedAdmin(String path, MockHttpServletRequestBuilder builder, ResultMatcher checkStatus) throws Exception {
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .id(-1L)
                .username("admin")
                .password("")
                .fullName("admin")
                .email("test@test.ru")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .authorities(List.of(new Authority(-1L, "ROLE_ADMIN")))
                .build();

        when(jwtTokenProvider.getUsername("asdlkjadshfklsdfjals")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);

        mockMvc.perform(builder)
                .andExpect(checkStatus);
    }

    private static Stream<Arguments> generateDataForAdmin() {
        Cookie cookie = new Cookie("userToken", "asdlkjadshfklsdfjals");
        cookie.setPath("/");
        cookie.setMaxAge(365 * 24 * 60 * 60);

        return Stream.of(
                Arguments.of("get /api/v1/security/authority", get("/api/v1/security/authority").cookie(cookie), status().isForbidden()),
                Arguments.of("put /api/v1/security/user/{id}/enabled/{enabled}", put("/api/v1/security/user/1/enabled/true").cookie(cookie), status().isForbidden()),
                Arguments.of("put /api/v1/security/user/{userId}/authority/{authorityId}", put("/api/v1/security/user/1/authority/1").cookie(cookie), status().isForbidden()),
                Arguments.of("get /api/v1/security/user/{id}", get("/api/v1/security/user/1").cookie(cookie), status().isForbidden()),
                Arguments.of("get /api/v1/security/user", get("/api/v1/security/user").cookie(cookie), status().isForbidden())
        );
    }

    private static Stream<Arguments> generateDataForSecurity() {
        Cookie cookie = new Cookie("userToken", "asdlkjadshfklsdfjals");
        cookie.setPath("/");
        cookie.setMaxAge(365 * 24 * 60 * 60);

        return Stream.of(
                Arguments.of("get /api/v1/security/authority", get("/api/v1/security/authority").cookie(cookie), status().isOk()),
                Arguments.of("put /api/v1/security/user/{id}/enabled/{enabled}", put("/api/v1/security/user/1/enabled/true").cookie(cookie), status().isOk()),
                Arguments.of("put /api/v1/security/user/{userId}/authority/{authorityId}", put("/api/v1/security/user/1/authority/1").cookie(cookie), status().isOk()),
                Arguments.of("get /api/v1/security/user/{id}", get("/api/v1/security/user/1").cookie(cookie), status().isOk()),
                Arguments.of("get /api/v1/security/user", get("/api/v1/security/user").cookie(cookie), status().isOk())
        );
    }

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of("get /api/v1/security/authority", get("/api/v1/security/authority")),
                Arguments.of("put /api/v1/security/user/{id}/enabled/{enabled}", put("/api/v1/security/user/1/enabled/true")),
                Arguments.of("put /api/v1/security/user/{userId}/authority/{authorityId}", put("/api/v1/security/user/1/authority/1")),
                Arguments.of("get /api/v1/security/user/{id}", get("/api/v1/security/user/1")),
                Arguments.of("get /api/v1/security/user", get("/api/v1/security/user"))
        );
    }
}
