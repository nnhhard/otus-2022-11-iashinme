package ru.iashinme.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookCustomRepository {

    Mono<Book> findByName(String name);

    @Override
    Mono<Void> deleteById(String id);

    Mono<Boolean> existsBookByAuthor_Id(String authorId);
}