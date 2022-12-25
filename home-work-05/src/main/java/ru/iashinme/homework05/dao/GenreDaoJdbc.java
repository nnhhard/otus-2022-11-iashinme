package ru.iashinme.homework05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.iashinme.homework05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations().queryForObject("select count(*) from genres", Integer.class);
        return isNull(count) ? 0 : count;
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into genres (name) values (:name)",
                params,
                kh,
                new String[]{"id"}
        );

        return isNull(kh.getKey()) ? 0 : kh.getKey().longValue();
    }

    @Override
    public int update(Genre genre) {
        return jdbc.update("update genres set name = :name where id = :id",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject(
                "select id, name from genres where id = :id", Map.of("id", id), new GenreMapper()
        );
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from genres", new GenreMapper());
    }

    @Override
    public int deleteById(long id) {
        return jdbc.update("delete from genres where id = :id", Map.of("id", id));
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
