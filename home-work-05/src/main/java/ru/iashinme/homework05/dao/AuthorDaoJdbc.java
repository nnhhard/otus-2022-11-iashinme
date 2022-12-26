package ru.iashinme.homework05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.iashinme.homework05.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations().queryForObject("select count(*) from authors", Integer.class);
        return isNull(count) ? 0 : count;
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("surname", author.getSurname());
        params.addValue("name", author.getName());
        params.addValue("patronymic", author.getPatronymic());

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into authors (surname, name, patronymic) values (:surname, :name, :patronymic)",
                params,
                kh,
                new String[]{"id"}
        );

        return isNull(kh.getKey()) ? 0 : kh.getKey().longValue();
    }

    @Override
    public int update(Author author) {
        return jdbc.update("update authors set surname = :surname, name = :name, patronymic = :patronymic where id = :id",
                Map.of("id", author.getId(),
                        "surname", author.getSurname(),
                        "name", author.getName(),
                        "patronymic", author.getPatronymic()));
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject(
                "select id, surname, name, patronymic from authors where id = :id",
                Map.of("id", id),
                new AuthorMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, surname, name, patronymic from authors", new AuthorMapper());
    }

    @Override
    public int deleteById(long id) {
        return jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            String patronymic = resultSet.getString("patronymic");
            return new Author(id, surname, name, patronymic);
        }
    }
}
