package ru.iashinme.blog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.iashinme.blog.dto.*;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.feign.StorageServiceProxy;
import ru.iashinme.blog.mapper.PostMapper;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.model.Post;
import ru.iashinme.blog.model.Technology;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

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

    @MockBean
    private StorageServiceProxy storageServiceProxy;

    private final static Post POST_ENTITY = Post.builder()
            .id(-1L)
            .technology(Technology.builder().id(-1L).name("name").build() )
            .title("title")
            .text("text")
            .imageGuid("123")
            .author(User.builder().id(-1L).fullName("fullName").build())
            .build();
    private final static PostDto POST_DTO = PostDto.builder()
            .text(POST_ENTITY.getText())
            .technology(TechnologyDto.builder().id(-1L).name("name").build())
            .title(POST_ENTITY.getTitle())
            .author(UserDto.builder().id(-1L).email("email").fullName("fullname").build())
            .build();

    private static final CustomUserDetails USER = CustomUserDetails.builder()
            .id(-1L)
            .email("email")
            .fullName("fullname")
            .authorities(List.of(new Authority(-1L, "ROLE_USER")))
            .build();

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки текста поста")
    public void shouldHaveCorrectExceptionForPostText() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .technologyId(-1L)
                .title("title")
                .build();

        assertThatThrownBy(() -> postService.save(postRequestDto, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Text is null or empty!");
    }

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки заголовка поста")
    public void shouldHaveCorrectExceptionForPostTitle() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .technologyId(-1L)
                .text("text")
                .build();

        assertThatThrownBy(() -> postService.save(postRequestDto, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Title is null or empty!");
    }

    @Test
    @DisplayName("корректно сохранять пост")
    public void shouldHaveCorrectSavePost() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .technologyId(-1L)
                .text("text")
                .title("text")
                .build();

        when(technologyService.findById(POST_ENTITY.getTechnology().getId())).thenReturn(POST_DTO.getTechnology());
        when(postRepository.save(any())).thenReturn(POST_ENTITY);
        when(postMapper.entityToDto(POST_ENTITY)).thenReturn(POST_DTO);

        var actualPostDto = postService.save(postRequestDto, USER);

        assertThat(actualPostDto)
                .isEqualTo(POST_DTO);
    }

    @DisplayName("возвращать ожидаемый список постов")
    @Test
    void shouldReturnExpectedPostList() {
        when(postRepository.findAll()).thenReturn(List.of(POST_ENTITY));
        when(postMapper.entityToDto(List.of(POST_ENTITY))).thenReturn(List.of(POST_DTO));

        var actualList = postService.findAll();

        assertThat(actualList).usingRecursiveFieldByFieldElementComparator().containsExactly(POST_DTO);
    }

    @Test
    @DisplayName("корректно удалять пост")
    public void shouldHaveCorrectDeletePost() {
        when(postRepository.findById(POST_ENTITY.getId())).thenReturn(Optional.of(POST_ENTITY));
        postService.delete(-1L);

        verify(postRepository, times(1)).deleteById(-1L);
        verify(storageServiceProxy, times(1)).deleteFile(POST_ENTITY.getImageGuid());
    }

    @Test
    @DisplayName("корректно находить пост по id")
    public void shouldHaveCorrectFindPostById() {
        when(postRepository.findById(POST_ENTITY.getId())).thenReturn(Optional.of(POST_ENTITY));
        when(postMapper.entityToDto(POST_ENTITY)).thenReturn(POST_DTO);

        var post = postService.findById(POST_ENTITY.getId());

        assertThat(post).isEqualTo(POST_DTO);
    }

    @Test
    @DisplayName("корректно находить все посты по id автора")
    public void shouldHaveCorrectFindPostsByAuthorId() {
        when(postRepository.findAllByAuthorId(POST_ENTITY.getAuthor().getId())).thenReturn(List.of(POST_ENTITY));
        when(postMapper.entityToDto(List.of(POST_ENTITY))).thenReturn(List.of(POST_DTO));

        var posts = postService.findAllByAuthor(POST_ENTITY.getAuthor().getId());

        assertThat(posts).isEqualTo(List.of(POST_DTO));
    }

    @Test
    @DisplayName("корректно находить все посты по id технолигии")
    public void shouldHaveCorrectFindPostsByTechnologyId() {
        when(postRepository.findAllByTechnologyId(POST_ENTITY.getTechnology().getId())).thenReturn(List.of(POST_ENTITY));
        when(postMapper.entityToDto(List.of(POST_ENTITY))).thenReturn(List.of(POST_DTO));

        var posts = postService.findAllByTechnology(POST_ENTITY.getTechnology().getId());

        assertThat(posts).isEqualTo(List.of(POST_DTO));
    }

    @Test
    @DisplayName("корректно изменять пост")
    public void shouldHaveCorrectUpdatePost() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .id(-1L)
                .technologyId(-1L)
                .text("text")
                .title("text")
                .build();

        when(postRepository.findById(postRequestDto.getId())).thenReturn(Optional.of(POST_ENTITY));
        when(postRepository.save(any())).thenReturn(POST_ENTITY);
        when(postMapper.entityToDto(POST_ENTITY)).thenReturn(POST_DTO);
        when(technologyService.findById(POST_ENTITY.getTechnology().getId())).thenReturn(POST_DTO.getTechnology());

        var actualPostDto = postService.edit(postRequestDto, USER);

        assertThat(actualPostDto)
                .isEqualTo(POST_DTO);
    }

    @Test
    @DisplayName("корректно выбрасывать исключение при измении пост с пустым загорловком")
    public void shouldHaveCorrectReturnExceptionForUpdatePostWithEmptyTitle() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .id(-1L)
                .technologyId(-1L)
                .text("text")
                .build();

        assertThatThrownBy(() -> postService.edit(postRequestDto, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Title is null or empty!");
    }

    @Test
    @DisplayName("корректно выбрасывать исключение при измении пост с пустым текстом")
    public void shouldHaveCorrectReturnExceptionForUpdatePostWithEmptyText() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .id(-1L)
                .technologyId(-1L)
                .title("title")
                .build();

        assertThatThrownBy(() -> postService.edit(postRequestDto, USER))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Text is null or empty!");
    }

    @Test
    @DisplayName("корректно выкидывать исключение при попытки изменить не свой пост")
    public void shouldHaveCorrectReturnExceptionByUpdatePost() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .id(-1L)
                .technologyId(-1L)
                .text("text")
                .title("title")
                .build();

        when(postRepository.findById(postRequestDto.getId())).thenReturn(Optional.of(POST_ENTITY));

        CustomUserDetails otherUser = CustomUserDetails.builder()
                .id(-2L)
                .build();

        assertThatThrownBy(() -> postService.edit(postRequestDto, otherUser))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("You are not the author of the post!");
    }

    @Test
    @DisplayName("корректно выкидывать исключение при попытки изменить не свой пост")
    public void shouldHaveCorrectReturnExceptionByUpdatePost1() {
        String guid = UUID.randomUUID().toString();
        when(postRepository.findById(-1L)).thenReturn(Optional.of(POST_ENTITY));
        when(storageServiceProxy.uploadFile(any())).thenReturn(
                ResponseEntity.ok().body(guid)
        );

        Post postWithImageGuid = Post.builder()
                .id(-1L)
                .imageGuid(guid)
                .build();
        PostDto postDtoWithImageGuid = PostDto.builder()
                .id(-1L).imageGuid(guid).build();

        when(postRepository.save(any())).thenReturn(postWithImageGuid);
        when(postMapper.entityToDto(postWithImageGuid)).thenReturn(postDtoWithImageGuid);

        var actualGuid = postService.uploadImageInPost(-1L, mock(MultipartFile.class));

        assertThat(actualGuid.getImageGuid()).isEqualTo(guid);

    }
}
