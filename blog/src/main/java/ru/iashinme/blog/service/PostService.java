package ru.iashinme.blog.service;

import lombok.NonNull;
import ru.iashinme.blog.dto.PostDto;
import ru.iashinme.blog.dto.PostRequestDto;
import ru.iashinme.blog.model.User;

import java.util.List;

public interface PostService {

    Long count();

    List<PostDto> findAll();

    List<PostDto> findAllByTechnology(@NonNull Long technologyId);

    List<PostDto> findAllByAuthor(@NonNull Long authorId);

    List<PostDto> search(@NonNull String search);

    PostDto findById(Long id);

    PostDto save(PostRequestDto postRequestDto, User user);
    PostDto edit(PostRequestDto postRequestDto, User user);

    void delete(Long id);
}