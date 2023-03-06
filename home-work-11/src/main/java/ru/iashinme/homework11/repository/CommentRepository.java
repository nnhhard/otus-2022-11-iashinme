package ru.iashinme.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findByBookId(String bookId);
    Mono<Void> deleteByBook_Id(String bookId);
}
