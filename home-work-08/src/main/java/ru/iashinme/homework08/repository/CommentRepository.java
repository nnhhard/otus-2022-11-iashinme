package ru.iashinme.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.iashinme.homework08.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByBookId(String bookId);
    void deleteAllByBookId(String bookId);
}
