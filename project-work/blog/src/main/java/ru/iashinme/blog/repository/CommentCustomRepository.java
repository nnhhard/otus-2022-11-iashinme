package ru.iashinme.blog.repository;

import ru.iashinme.blog.dto.CommentDto;

import java.util.List;

public interface CommentCustomRepository {

    List<CommentDto> findByPostId(Long postId);
}
