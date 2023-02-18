package ru.iashinme.homework11.repository;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.model.Author;
import ru.iashinme.homework11.model.Book;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<UpdateResult> updateAuthorInBook(Author author) {
        Query query = new Query(Criteria.where("author.id").is(author.getId()));

        Update update = new Update().set("author", author);

        return mongoTemplate.updateMulti(query, update, Book.class);
    }
}