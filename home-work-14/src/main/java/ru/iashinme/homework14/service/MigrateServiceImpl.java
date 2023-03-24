package ru.iashinme.homework14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.iashinme.homework14.model.h2.AuthorSQL;
import ru.iashinme.homework14.model.h2.BookSQL;
import ru.iashinme.homework14.model.h2.GenreSQL;
import ru.iashinme.homework14.model.mongo.Author;
import ru.iashinme.homework14.model.mongo.Book;
import ru.iashinme.homework14.model.mongo.Genre;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MigrateServiceImpl implements MigrateService {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final Map<String, AuthorSQL> authorIdMap = new HashMap<>();
    private final Map<String, GenreSQL> genreIdMap = new HashMap<>();

    @Override
    public AuthorSQL authorConvert(Author author) {
        var id = getSequence("AUTHOR_SEQUENCE");
        var authorSql = new AuthorSQL(id, author.getName());
        authorIdMap.put(author.getId(), authorSql);
        return authorSql;
    }

    @Override
    public GenreSQL genreConvert(Genre genre) {
        var id = getSequence("GENRE_SEQUENCE");
        var genreSql = new GenreSQL(id, genre.getName());
        genreIdMap.put(genre.getId(), genreSql);
        return genreSql;
    }

    @Override
    public BookSQL bookConvert(Book book) {
        var id = getSequence("BOOK_SEQUENCE");
        var author = authorIdMap.get(book.getAuthor().getId());
        var genre = genreIdMap.get(book.getGenre().getId());
        return new BookSQL(id, book.getName(), genre, author);
    }

    private Long getSequence(String sequenceName) {
        var jdbc = namedParameterJdbcOperations.getJdbcOperations();
        return jdbc.queryForObject(String.format("select next value for %s", sequenceName), Long.class);
    }
}
