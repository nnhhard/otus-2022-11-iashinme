package ru.iashin.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.iashin.homework06.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
public class CommentRepositoryJpaTest {
    private static final long FIRST_COMMENT_ID = -1L;
    private static final long BOOK_ID = -1L;

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном комментарии по его id")
    @Test
    void shouldFindExpectedCommentById() {
        Optional<Comment> optionalActualComment = commentRepositoryJpa.findById(FIRST_COMMENT_ID);
        Comment expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(optionalActualComment)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать все комментарии по id книги")
    @Test
    void shouldFindExpectedCommentByBookId() {
        List<Comment> commentsActual = commentRepositoryJpa.findByBookId(BOOK_ID);
        List<Comment> expectedComments = commentRepositoryJpa.findByBookId(BOOK_ID);

        assertThat(expectedComments)
                .hasSize(commentsActual.size())
                .isEqualTo(commentsActual);
    }

    @DisplayName("удалять комментарий по его id")
    @Test
    void shouldDeleteCommentById() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        em.detach(comment);

        commentRepositoryJpa.deleteById(comment.getId());
        var findComment = commentRepositoryJpa.findById(comment.getId());

        assertThat(findComment).isNotPresent();
    }

    @DisplayName("удалять комментарии по id книги")
    @Test
    void shouldDeleteCommentsByBookId() {
        List<Comment> commentsAfterDelete = commentRepositoryJpa.findByBookId(BOOK_ID);
        commentRepositoryJpa.deleteByBookId(BOOK_ID);
        List<Comment> commentsActual = commentRepositoryJpa.findByBookId(BOOK_ID);

        assertThat(commentsActual.size()).isEqualTo(0).isNotEqualTo(commentsAfterDelete.size());
    }

    @DisplayName("изменять комментарий по его id")
    @Test
    void shouldUpdateCommentById() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        String oldMessageComment = comment.getMessageComment();
        String newMessageComment = "Книга огонь!!!";
        em.detach(comment);

        comment.setMessageComment(newMessageComment);
        Comment actualComment = commentRepositoryJpa.save(comment);

        assertThat(actualComment.getMessageComment())
                .isNotEqualTo(oldMessageComment)
                .isEqualTo(newMessageComment);
    }
}
