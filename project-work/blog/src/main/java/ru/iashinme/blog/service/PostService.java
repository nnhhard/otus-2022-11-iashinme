package ru.iashinme.blog.service;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.iashinme.blog.dto.CustomUserDetails;
import ru.iashinme.blog.dto.PostDto;
import ru.iashinme.blog.dto.PostRequestDto;

import java.util.List;

public interface PostService {

    List<PostDto> findAll();

    List<PostDto> findAllByTechnology(@NonNull Long technologyId);

    List<PostDto> findAllByAuthor(@NonNull Long authorId);

    List<PostDto> search(@NonNull String search);

    PostDto findById(Long id);

    PostDto save(PostRequestDto postRequestDto, CustomUserDetails user);
    PostDto edit(PostRequestDto postRequestDto, CustomUserDetails user);

    void delete(Long id);

    PostDto uploadImageInPost(Long id, MultipartFile file, CustomUserDetails user);

    ResponseEntity<byte[]> downloadImageInPost(Long fileId);
}