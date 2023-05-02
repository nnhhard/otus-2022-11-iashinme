package ru.iashinme.blog.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.blog.dto.PostDto;
import ru.iashinme.blog.dto.PostRequestDto;
import ru.iashinme.blog.dto.TechnologyDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.PostMapper;
import ru.iashinme.blog.model.Post;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.repository.PostRepository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TechnologyService technologyService;
    private final PostMapper postMapper;

    @Override
    @Transactional(readOnly = true)
    public Long count() {
        return postRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> findAll() {
        return postMapper.entityToDto(postRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> findAllByTechnology(@NonNull Long technologyId) {
        return postMapper.entityToDto(postRepository.findAllByTechnologyId(technologyId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> findAllByAuthor(@NonNull Long authorId) {
        return postMapper.entityToDto(postRepository.findAllByAuthorId(authorId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> search(@NonNull String search) {
        return postMapper.entityToDto(postRepository.findAllByTitleContainingIgnoreCase(search));
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto findById(Long id) {
        return postMapper.entityToDto(
                postRepository.findById(id).orElseThrow(
                        () -> new ValidateException("Post not found.")
                )
        );
    }

    @Override
    @Transactional
    public PostDto save(PostRequestDto postRequestDto, User user) {
        validate(postRequestDto);

        if(postRequestDto.getId() != null) {
            throw new ValidateException("You cannot set the id when creating!");
        }

        TechnologyDto technology = technologyService.findById(postRequestDto.getTechnologyId());

        Post post = Post
                    .builder()
                    .title(postRequestDto.getTitle())
                    .text(postRequestDto.getText())
                    .author(user)
                    .technology(technology.toEntity())
                    .build();

        return postMapper.entityToDto(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostDto edit(PostRequestDto postRequestDto, User user) {
        validate(postRequestDto);

        Post post = postRepository.findById(postRequestDto.getId()).orElseThrow(
                () -> new ValidateException("Post not found!")
        );

        if(!Objects.equals(post.getAuthor().getId(), user.getId())) {
            throw new ValidateException("You are not the author of the post!");
        }

        TechnologyDto technology = technologyService.findById(postRequestDto.getTechnologyId());

        post.setTitle(postRequestDto.getTitle());
        post.setText(postRequestDto.getText());
        post.setTechnology(technology.toEntity());

        return postMapper.entityToDto(postRepository.save(post));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    private void validate(PostRequestDto post) {
        if (StringUtils.isBlank(post.getTitle())) {
            throw new ValidateException("Title is null or empty!");
        }

        if (StringUtils.isBlank(post.getText())) {
            throw new ValidateException("Text is null or empty!");
        }
    }
}