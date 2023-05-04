package ru.iashinme.blog.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.iashinme.blog.dto.CommentDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DisplayName("Jdbc репозиторий для работы с комментариями должен ")
public class CommentCustomRepositoryImplTest extends AbstractIntegrationDbTest {

    private static final List<CommentDto> EXPECTED_LIST_COMMENTS = List.of(
            CommentDto.builder()
                    .id(-1L)
                    .text("comment text")
                    .authorFullName("Test2")
                    .time(LocalDateTime.of(2023, 5, 1, 0, 0, 0))
                    .build(),
            CommentDto.builder()
                    .id(-2L)
                    .text("comment text 2")
                    .authorFullName("Test")
                    .time(LocalDateTime.of(2023, 5, 2, 0, 0, 0))
                    .build()
    );

    @Autowired
    private CommentCustomRepositoryImpl commentCustomRepository;

    @Test
    @Sql(scripts = "/sql/insert_comments.sql")
    @DisplayName("корреткно возвращать список комментарий по id поста")
    public void shouldCorrectReturnListCommentDto() {
        var comments = commentCustomRepository.findByPostId(-1L);

        assertThat(comments)
                .containsExactlyInAnyOrderElementsOf(EXPECTED_LIST_COMMENTS);
    }

    @Test
    @DisplayName("корреткно возвращать пустой список, если для поста их нет")
    public void shouldCorrectReturnEmptyList() {
        List<CommentDto> comments = commentCustomRepository.findByPostId(-1L);

        assertThat(comments)
                .containsExactlyInAnyOrderElementsOf(Collections.emptyList());
    }

}

