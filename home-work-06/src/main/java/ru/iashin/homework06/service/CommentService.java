package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.CommentWithBookIdNameGenreDto;
import ru.iashin.homework06.dto.CommentWithoutBookDto;

import java.util.List;

public interface CommentService {

    CommentWithBookIdNameGenreDto createComment(long bookId, String messageComment);

    CommentWithBookIdNameGenreDto updateComment(long id, String messageComment);

    CommentWithBookIdNameGenreDto getCommentById(long id);

    List<CommentWithoutBookDto> getAllCommentsByBookId(long bookId);

    void deleteCommentById(long id);
}
