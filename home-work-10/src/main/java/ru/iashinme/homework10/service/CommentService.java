package ru.iashinme.homework10.service;

import ru.iashinme.homework10.dto.CommentDto;
import ru.iashinme.homework10.dto.CommentWithoutBookDto;

import java.util.List;

public interface CommentService {

    CommentDto create(long bookId, String messageComment);

    CommentDto update(long id, String messageComment);

    CommentDto findById(long id);

    List<CommentWithoutBookDto> findAllByBookId(long bookId);

    void deleteById(long id);
}
