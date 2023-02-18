package ru.iashinme.homework11.repository;

import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.model.Author;

public interface BookCustomRepository {

    Mono<UpdateResult> updateAuthorInBook(Author author);
}
