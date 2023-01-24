package ru.iashinme.homework07.service;

import ru.iashinme.homework07.dto.CommentWithBookIdNameGenreDto;
import ru.iashinme.homework07.dto.CommentWithoutBookDto;

import java.util.List;

public interface CommentService {

    CommentWithBookIdNameGenreDto createComment(long bookId, String messageComment);

    CommentWithBookIdNameGenreDto updateComment(long id, String messageComment);

    CommentWithBookIdNameGenreDto getCommentById(long id);

    List<CommentWithoutBookDto> getAllCommentsByBookId(long bookId);

    void deleteCommentById(long id);
}
