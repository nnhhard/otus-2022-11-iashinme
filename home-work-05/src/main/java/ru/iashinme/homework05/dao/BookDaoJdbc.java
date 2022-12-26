package ru.iashinme.homework05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.iashinme.homework05.domain.Author;
import ru.iashinme.homework05.domain.Book;
import ru.iashinme.homework05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations().queryForObject("select count(*) from books", Integer.class);
        return isNull(count) ? 0 : count;
    }

    @Override
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource(
                Map.of("name", book.getName(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId())
        );

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into books (name, author_id, genre_id) values (:name, :author_id, :genre_id)",
                params,
                kh,
                new String[]{"id"}
        );

        return isNull(kh.getKey()) ? 0 : kh.getKey().longValue();
    }

    @Override
    public int update(Book book) {
        return jdbc.update("update books set name = :name, author_id = :author_id, genre_id = :genre_id  where id = :id",
                Map.of("id", book.getId(),
                        "name", book.getName(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject(
                "select books.id, books.name, authors.id as author_id, authors.surname as author_surname, authors.name as author_name," +
                        "authors.patronymic as author_patronymic, genres.id as genre_id, genres.name as genre_name " +
                        "from books " +
                        "join authors on books.author_id = authors.id " +
                        "join genres on books.genre_id = genres.id " +
                        "where books.id = :id",
                Map.of("id", id),
                new BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select books.id, books.name, authors.id as author_id, authors.surname as author_surname, authors.name as author_name," +
                "authors.patronymic as author_patronymic, genres.id as genre_id, genres.name as genre_name " +
                "from books " +
                "inner join authors on books.author_id = authors.id " +
                "inner join genres on books.genre_id = genres.id ", new BookMapper());
    }

    @Override
    public int deleteById(long id) {
        return jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author author = new Author(
                    resultSet.getLong("author_id"),
                    resultSet.getString("author_surname"),
                    resultSet.getString("author_name"),
                    resultSet.getString("author_patronymic")
            );
            Genre genre = new Genre(
                    resultSet.getLong("genre_id"),
                    resultSet.getString("genre_name")
            );
            return new Book(id, name, author, genre);
        }
    }
}
