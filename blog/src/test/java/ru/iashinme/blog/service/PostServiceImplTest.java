package ru.iashinme.blog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.blog.dto.PostDto;
import ru.iashinme.blog.dto.PostRequestDto;
import ru.iashinme.blog.dto.TechnologyDto;
import ru.iashinme.blog.dto.UserSmallDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.PostMapper;
import ru.iashinme.blog.model.Post;
import ru.iashinme.blog.model.Technology;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.repository.PostRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с постами ")
@SpringBootTest(classes = PostServiceImpl.class)
public class PostServiceImplTest {

    @Autowired
    private PostServiceImpl postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private TechnologyService technologyService;

    @MockBean
    private PostMapper postMapper;

    private final static Post EXPECTED_POST_ENTITY = Post.builder()
            .id(-1L)
            .technology(Technology.builder().id(-1L).name("name").build() )
            .title("title")
            .text("text")
            .author(User.builder().fullName("fullName").build())
            .build();
    private final static PostDto EXPECTED_POST_DTO = PostDto.builder()
            .text(EXPECTED_POST_ENTITY.getText())
            .technology(TechnologyDto.builder().id(-1L).name("name").build())
            .title(EXPECTED_POST_ENTITY.getTitle())
            .author(UserSmallDto.builder().id(-1L).email("email").fullName("fullname").build())
            .build();

    private final static PostRequestDto EXPECTED_POST = PostRequestDto.builder()
            .technologyId(-1L)
            .text("text")
            .title("title")
            .build();

    private final static Long EXPECTED_POST_COUNT = 1L;

    private static final User USER = User.builder().id(-1L).email("email").fullName("fullname").build();

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки текста поста")
    public void shouldHaveCorrectExceptionForPostText() {
        EXPECTED_POST.setText("");
        EXPECTED_POST.setTitle("title");

        assertThatThrownBy(() -> postService.save(EXPECTED_POST, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Text is null or empty!");
    }

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки заголовка поста")
    public void shouldHaveCorrectExceptionForPostTitle() {
        EXPECTED_POST.setText("text");
        EXPECTED_POST.setTitle("");

        assertThatThrownBy(() -> postService.save(EXPECTED_POST, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Title is null or empty!");
    }

    @DisplayName("возвращать ожидаемое количество постов")
    @Test
    void shouldReturnExpectedPostCount() {
        when(postRepository.count()).thenReturn(EXPECTED_POST_COUNT);
        long actualCount = postService.count();

        assertThat(actualCount)
                .isEqualTo(EXPECTED_POST_COUNT);
    }

    @DisplayName("возвращать ожидаемый список постов")
    @Test
    void shouldReturnExpectedPostList() {
        when(postRepository.findAll()).thenReturn(List.of(EXPECTED_POST_ENTITY));
        when(postMapper.entityToDto(List.of(EXPECTED_POST_ENTITY))).thenReturn(List.of(EXPECTED_POST_DTO));

        var actualList = postService.findAll();

        assertThat(actualList).usingRecursiveFieldByFieldElementComparator().containsExactly(EXPECTED_POST_DTO);
    }
}
