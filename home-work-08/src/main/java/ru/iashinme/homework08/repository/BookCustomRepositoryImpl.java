package ru.iashinme.homework08.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import ru.iashinme.homework08.model.Book;
import ru.iashinme.homework08.model.Genre;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public long updateGenreInBook(Genre genre) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genre.id").is(genre.getId()));
        Update update = new Update();
        update.set("genre", genre);
        return mongoTemplate.updateMulti(query, update, Book.class).getModifiedCount();
    }
}