package ru.iashinme.blog.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.blog.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(value = "Post.Author.Technology")
    List<Post> findAll();

    @EntityGraph(value = "Post.Author.Technology")
    List<Post> findAllByTitleContainingIgnoreCase(String search);

    @EntityGraph(value = "Post.Author.Technology")
    List<Post> findAllByTechnologyId(Long technologyId);

    @EntityGraph(value = "Post.Author.Technology")
    List<Post> findAllByAuthorId(Long authorId);

}
