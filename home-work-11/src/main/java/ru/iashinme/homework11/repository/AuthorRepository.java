package ru.iashinme.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findBySurnameAndNameAndPatronymic(String surname, String name, String patronymic);
    Mono<Void> deleteById(String id);
    Mono<Boolean> existsById(String id);
}
