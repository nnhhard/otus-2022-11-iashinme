package ru.iashinme.blog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.iashinme.blog.dto.CommentDto;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final static String SQL =
            "select c.id, c.text, c.time, u.full_name as author_full_name " +
                    "from blog.comments c " +
                    "join blog.users u on u.id = c.author_id " +
                    "where c.post_id = :post_id";

    private final DataClassRowMapper<CommentDto> rowMapper
            = new DataClassRowMapper<>(CommentDto.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<CommentDto> findByPostId(Long postId) {
        return jdbcTemplate.query(SQL, Map.of("post_id", postId), rowMapper);
    }

}
