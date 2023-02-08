package ru.iashinme.homework09.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework09.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(long bookId);

    @EntityGraph(value = "comment-book-graph")
    Optional<Comment> findById(long id);
}
