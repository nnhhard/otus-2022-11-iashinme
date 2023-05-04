package ru.iashinme.blog.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.blog.dto.AuthorityDto;
import ru.iashinme.blog.dto.PostDto;
import ru.iashinme.blog.dto.TechnologyDto;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.model.Post;
import ru.iashinme.blog.model.Technology;
import ru.iashinme.blog.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Маппер для преобразования Post(entity) в PostDto ")
@SpringBootTest(classes = PostMapper.class)
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @MockBean
    private TechnologyMapper technologyMapper;

    @MockBean
    private UserMapper userMapper;

    private final static Technology TECHNOLOGY_ENTITY = Technology.builder()
            .id(-1L)
            .name("cpp")
            .build();

    private static final TechnologyDto TECHNOLOGY_DTO = TechnologyDto
            .builder()
            .id(TECHNOLOGY_ENTITY.getId())
            .name(TECHNOLOGY_ENTITY.getName())
            .build();

    private static final User USER_ENTITY = User.builder()
            .id(-1L)
            .email("email")
            .enabled(true)
            .authority(new Authority(-1L, "ROLE_USER"))
            .fullName("fullname")
            .username("username")
            .password("password")
            .build();

    private static final UserDto USER_DTO = UserDto.builder()
            .id(USER_ENTITY.getId())
            .username(USER_ENTITY.getUsername())
            .enabled(USER_ENTITY.isEnabled())
            .authority(new AuthorityDto(-1L, "ROLE_USER"))
            .email(USER_ENTITY.getEmail())
            .fullName(USER_ENTITY.getFullName())
            .build();

    private static final Post POST_ENTITY = Post.builder()
            .id(-3L)
            .text("text")
            .title("title")
            .author(USER_ENTITY)
            .technology(TECHNOLOGY_ENTITY)
            .build();

    private static final PostDto POST_DTO = PostDto.builder()
            .id(POST_ENTITY.getId())
            .text(POST_ENTITY.getText())
            .title(POST_ENTITY.getTitle())
            .author(USER_DTO)
            .technology(TECHNOLOGY_DTO)
            .build();

    @Test
    @DisplayName("должен возарвщать ожидаемый PostDto")
    public void shouldCorrectReturnPostDto() {
        when(technologyMapper.entityToDto(TECHNOLOGY_ENTITY)).thenReturn(TECHNOLOGY_DTO);
        when(userMapper.entityToDto(USER_ENTITY)).thenReturn(USER_DTO);

        PostDto actualPostDto = postMapper.entityToDto(POST_ENTITY);

        assertThat(actualPostDto)
                .usingRecursiveComparison()
                .isEqualTo(POST_DTO);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список PostDto")
    public void shouldCorrectReturnListPostDto() {
        when(technologyMapper.entityToDto(TECHNOLOGY_ENTITY)).thenReturn(TECHNOLOGY_DTO);
        when(userMapper.entityToDto(USER_ENTITY)).thenReturn(USER_DTO);

        List<PostDto> actualPostDto = postMapper.entityToDto(List.of(POST_ENTITY));

        assertThat(actualPostDto)
                .usingRecursiveComparison()
                .isEqualTo(List.of(POST_DTO));
    }
}
