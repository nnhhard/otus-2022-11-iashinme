package ru.iashinme.homework08.service;

import ru.iashinme.homework08.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(String bookId, String messageComment);

    CommentDto updateComment(String id, String messageComment);

    CommentDto getCommentById(String id);

    List<CommentDto> getAllCommentsByBookId(String bookId);

    void deleteCommentById(String id);
}
