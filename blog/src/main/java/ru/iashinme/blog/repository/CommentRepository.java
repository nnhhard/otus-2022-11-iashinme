package ru.iashinme.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.blog.model.Comment;

public interface CommentRepository  extends JpaRepository<Comment, Long>, CommentCustomRepository {

}
