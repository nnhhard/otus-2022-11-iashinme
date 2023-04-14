package ru.iashinme.homework17.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iashinme.homework17.dto.AuthorDto;
import ru.iashinme.homework17.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Маппер для преобразования Author(entity) в AuthorDto ")
@SpringBootTest(classes = AuthorMapper.class)
public class AuthorMapperTest {

    @Autowired
    private AuthorMapper authorMapper;

    private static final Author AUTHOR_ENTITY_FIRST =
            new Author(-1, "surname", "name", "patron");


    private static final AuthorDto EXPECTED_AUTHOR_DTO_FIRST = AuthorDto
            .builder()
            .id(AUTHOR_ENTITY_FIRST.getId())
            .surname(AUTHOR_ENTITY_FIRST.getSurname())
            .name(AUTHOR_ENTITY_FIRST.getName())
            .patronymic(AUTHOR_ENTITY_FIRST.getPatronymic())
            .build();

    private static final Author AUTHOR_ENTITY_SECOND =
            new Author(-2, "surname2", "name2", "patron2");
    private static final AuthorDto EXPECTED_AUTHOR_DTO_SECOND = AuthorDto
            .builder()
            .id(AUTHOR_ENTITY_SECOND.getId())
            .surname(AUTHOR_ENTITY_SECOND.getSurname())
            .name(AUTHOR_ENTITY_SECOND.getName())
            .patronymic(AUTHOR_ENTITY_SECOND.getPatronymic())
            .build();

    @Test
    @DisplayName("должен возарвщать ожидаемый AuthorDto")
    public void shouldCorrectReturnAuthorDto() {
        AuthorDto actualAuthorDto = authorMapper.entityToDto(AUTHOR_ENTITY_FIRST);
        assertThat(actualAuthorDto)
                .usingRecursiveComparison()
                .isEqualTo(EXPECTED_AUTHOR_DTO_FIRST);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список AuthorDto")
    public void shouldCorrectReturnListAuthorDto() {
        var actualAuthorDtoList = authorMapper.entityToDto(
                List.of(AUTHOR_ENTITY_FIRST, AUTHOR_ENTITY_SECOND)
        );

        assertThat(actualAuthorDtoList).
                containsExactlyInAnyOrderElementsOf(
                        List.of(EXPECTED_AUTHOR_DTO_FIRST, EXPECTED_AUTHOR_DTO_SECOND)
                );
    }
}
