package ru.iashinme.blog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.blog.dto.AuthorityDto;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.UserMapper;
import ru.iashinme.blog.model.*;
import ru.iashinme.blog.repository.AuthorityRepository;
import ru.iashinme.blog.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с пользователями ")
@SpringBootTest(classes = SecurityServiceImpl.class)
public class SecurityServiceImplTest {

    @Autowired
    private SecurityServiceImpl securityService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthorityRepository authorityRepository;

    @MockBean
    private UserMapper userMapper;

    private final static Authority AUTHORITY_ROLE_USER = new Authority(-1L, "ROLE_USER");
    private final static AuthorityDto AUTHORITY_DTO_ROLE_USER = new AuthorityDto(-1L, "ROLE_USER");
    private final static Authority AUTHORITY_ROLE_ADMIN = new Authority(-2L, "ROLE_ADMIN");
    private final static AuthorityDto AUTHORITY_DTO_ROLE_ADMIN = new AuthorityDto(-2L, "ROLE_ADMIN");

    private final static User USER = User.builder()
            .id(-1L)
            .username("user")
            .password("")
            .fullName("user")
            .email("test@test.ru")
            .enabled(true)
            .authority(AUTHORITY_ROLE_USER)
            .build();

    private final static UserDto USER_DTO = UserDto.builder()
            .id(-1L)
            .username("user")
            .fullName("user")
            .email("test@test.ru")
            .enabled(true)
            .authority(AUTHORITY_DTO_ROLE_USER)
            .build();

    @Test
    @DisplayName("корректно выдавать список пользователей")
    public void shouldHaveCorrectReturnListUsers() {
        when(userRepository.findAll()).thenReturn(List.of(USER));
        when(userMapper.entityToDto(List.of(USER))).thenReturn(List.of(USER_DTO));

        var users = securityService.findAll();

        assertThat(users)
                .isEqualTo(List.of(USER_DTO));
    }

    @Test
    @DisplayName("корректно находить пользователя по id")
    public void shouldHaveCorrectReturnUserById() {
        when(userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));
        when(userMapper.entityToDto(USER)).thenReturn(USER_DTO);

        var user = securityService.findById(USER.getId());

        assertThat(user)
                .isEqualTo(USER_DTO);
    }

    @Test
    @DisplayName("корректно выбрасывать исключение если пользователь не найден")
    public void shouldHaveCorrectReturnExceptionForFindUserById() {
        when(userRepository.findById(USER.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> securityService.findById(USER.getId()))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("User not found!");
    }

    @Test
    @DisplayName("корректно блокировать пользователя")
    public void shouldHaveCorrectBlockUser() {
        when(userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));

        User userBlocked = User.builder()
                .id(-1L)
                .username("user")
                .password("")
                .fullName("user")
                .email("test@test.ru")
                .enabled(false)
                .authority(AUTHORITY_ROLE_USER)
                .build();

        UserDto userDtoBlocked = UserDto.builder()
                .id(-1L)
                .username("user")
                .fullName("user")
                .email("test@test.ru")
                .enabled(false)
                .authority(AUTHORITY_DTO_ROLE_USER)
                .build();

        when(userRepository.save(any())).thenReturn(userBlocked);
        when(userMapper.entityToDto(userBlocked)).thenReturn(userDtoBlocked);

        var user = securityService.userSetEnabled(USER.getId(), false);

        assertThat(user)
                .isEqualTo(userDtoBlocked);
    }

    @Test
    @DisplayName("корректно выдавать список Authority")
    public void shouldHaveCorrectReturnListAuthority() {
        when(authorityRepository.findAll()).thenReturn(List.of(AUTHORITY_ROLE_USER, AUTHORITY_ROLE_ADMIN));

        var users = securityService.findAllAuthority();

        assertThat(users)
                .isEqualTo(List.of(AUTHORITY_ROLE_USER, AUTHORITY_ROLE_ADMIN));
    }

    @Test
    @DisplayName("корректно изменять права доступа пользователя")
    public void shouldHaveCorrectSetAuthorityUser() {
        when(userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));
        when(authorityRepository.findById(AUTHORITY_ROLE_ADMIN.getId())).thenReturn(Optional.of(AUTHORITY_ROLE_ADMIN));

        User user = User.builder()
                .id(-1L)
                .username("user")
                .password("")
                .fullName("user")
                .email("test@test.ru")
                .enabled(false)
                .authority(AUTHORITY_ROLE_ADMIN)
                .build();

        UserDto userDtoAdmin = UserDto.builder()
                .id(-1L)
                .username("user")
                .fullName("user")
                .email("test@test.ru")
                .enabled(false)
                .authority(AUTHORITY_DTO_ROLE_ADMIN)
                .build();

        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.entityToDto(user)).thenReturn(userDtoAdmin);

        var actualUser = securityService.userSetAuthority(USER.getId(), AUTHORITY_ROLE_ADMIN.getId());

        assertThat(actualUser)
                .isEqualTo(userDtoAdmin);
    }

    @Test
    @DisplayName("корректно выбрасывать исключение при ненайденной Authority")
    public void shouldHaveCorrectExceptionForFindAuthority() {
        when(userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));
        when(authorityRepository.findById(AUTHORITY_ROLE_ADMIN.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> securityService.userSetAuthority(USER.getId(), AUTHORITY_ROLE_ADMIN.getId()))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Authority not found!");

    }


}
