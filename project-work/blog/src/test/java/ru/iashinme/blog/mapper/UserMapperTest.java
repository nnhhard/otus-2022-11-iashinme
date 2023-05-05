package ru.iashinme.blog.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.blog.dto.AuthorityDto;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Маппер для преобразования User(entity) в UserDto ")
@SpringBootTest(classes = UserMapper.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @MockBean
    private AuthorityMapper authorityMapper;

    private static final Authority AUTHORITY_ENTITY = new Authority(-1L, "ROLE_USER");

    private static final AuthorityDto AUTHORITY_DTO = new AuthorityDto(-1L, "ROLE_USER");

    private static final User USER = User.builder()
            .id(-1L)
            .email("email")
            .enabled(true)
            .authority(AUTHORITY_ENTITY)
            .fullName("fullname")
            .username("username")
            .password("password")
            .build();

    private static final UserDto USER_DTO = UserDto.builder()
            .id(USER.getId())
            .username(USER.getUsername())
            .enabled(USER.isEnabled())
            .authority(AUTHORITY_DTO)
            .email(USER.getEmail())
            .fullName(USER.getFullName())
            .build();

    @Test
    @DisplayName("должен возарвщать ожидаемый UserDto")
    public void shouldCorrectReturnUserDto() {
        when(authorityMapper.entityToDto(AUTHORITY_ENTITY)).thenReturn(AUTHORITY_DTO);

        UserDto actualUserDto = userMapper.entityToDto(USER);

        assertThat(actualUserDto)
                .usingRecursiveComparison()
                .isEqualTo(USER_DTO);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список UserDto")
    public void shouldCorrectReturnListUserDto() {
        when(authorityMapper.entityToDto(AUTHORITY_ENTITY)).thenReturn(AUTHORITY_DTO);

        List<UserDto> actualUserDto = userMapper.entityToDto(List.of(USER));

        assertThat(actualUserDto)
                .usingRecursiveComparison()
                .isEqualTo(List.of(USER_DTO));
    }
}
