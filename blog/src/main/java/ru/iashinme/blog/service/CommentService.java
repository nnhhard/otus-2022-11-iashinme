package ru.iashinme.blog.service;

import ru.iashinme.blog.dto.CommentDto;
import ru.iashinme.blog.dto.CommentRequestDto;
import ru.iashinme.blog.model.User;

import java.util.List;

public interface CommentService {

    CommentDto save(CommentRequestDto commentRequestDto, User user);

    CommentDto edit(CommentRequestDto commentRequestDto, User user);

    List<CommentDto> findAllByPostId(Long postId);

    CommentDto findById(Long id);

    void delete(Long commentId);
}
