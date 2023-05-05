package ru.iashinme.blog.service;

import ru.iashinme.blog.dto.CommentDto;
import ru.iashinme.blog.dto.CommentRequestDto;
import ru.iashinme.blog.dto.CustomUserDetails;

import java.util.List;

public interface CommentService {

    CommentDto save(CommentRequestDto commentRequestDto, CustomUserDetails user);

    CommentDto edit(CommentRequestDto commentRequestDto, CustomUserDetails user);

    List<CommentDto> findAllByPostId(Long postId);

    CommentDto findById(Long id);

    void delete(Long commentId);
}
