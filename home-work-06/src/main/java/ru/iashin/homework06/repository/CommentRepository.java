package ru.iashin.homework06.repository;

import ru.iashin.homework06.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findByBookId(long bookId);

    void deleteByBookId(long bookId);

    void deleteById(long id);
}
