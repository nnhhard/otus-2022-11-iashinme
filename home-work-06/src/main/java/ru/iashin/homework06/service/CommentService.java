package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long bookId, String messageComment);

    CommentDto updateComment(long id, String messageComment);

    CommentDto getCommentById(long id);

    List<CommentDto> getAllCommentsByBookId(long bookId);

    void deleteCommentById(long id);
}
