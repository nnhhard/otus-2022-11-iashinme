package ru.iashinme.homework18.service;

import ru.iashinme.homework18.dto.CommentDto;
import ru.iashinme.homework18.dto.CommentWithoutBookDto;

import java.util.List;

public interface CommentService {

    CommentDto create(long bookId, String messageComment);

    CommentDto update(long id, String messageComment);

    CommentDto findById(long id);

    List<CommentWithoutBookDto> findAllByBookId(long bookId);

    void deleteById(long id);
}
