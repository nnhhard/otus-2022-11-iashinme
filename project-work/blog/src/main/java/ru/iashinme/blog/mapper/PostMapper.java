package ru.iashinme.blog.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iashinme.blog.dto.PostDto;
import ru.iashinme.blog.model.Post;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final TechnologyMapper technologyMapper;
    private final UserMapper userMapper;

    public List<PostDto> entityToDto(List<Post> posts) {
        return posts.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public PostDto entityToDto(Post post) {
        return PostDto
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .technology(technologyMapper.entityToDto(post.getTechnology()))
                .author(userMapper.entityToDto(post.getAuthor()))
                .imageGuid(post.getImageGuid())
                .build();
    }
}
