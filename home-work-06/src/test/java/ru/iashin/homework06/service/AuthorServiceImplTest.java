package ru.iashin.homework06.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashin.homework06.dto.AuthorDto;
import ru.iashin.homework06.exception.ValidateException;
import ru.iashin.homework06.mapper.AuthorMapper;
import ru.iashin.homework06.model.Author;
import ru.iashin.homework06.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с авторами должен ")
@SpringBootTest(classes = AuthorServiceImpl.class)
public class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private AuthorMapper authorMapper;

    private static final long EXPECTED_AUTHOR_COUNT = 1;
    private static final Author EXPECTED_AUTHOR = new Author(-1, "surname", "name", "patronymic");
    private static final AuthorDto EXPECTED_AUTHOR_DTO = AuthorDto
            .builder()
            .id(EXPECTED_AUTHOR.getId())
            .surname(EXPECTED_AUTHOR.getSurname())
            .name(EXPECTED_AUTHOR.getName())
            .patronymic(EXPECTED_AUTHOR.getPatronymic())
            .build();

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки фамилии автора")
    public void shouldHaveCorrectExceptionForAuthorSurname() {
        Author author = new Author(-1L, "", "name", "patronymic");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        assertThatThrownBy(() -> authorService.createAuthor(author.getSurname(), author.getName(), author.getPatronymic()))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Author surname is null or empty!");
    }

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки имени автора")
    public void shouldHaveCorrectExceptionForAuthorName() {
        Author author = new Author(-1L, "surname", "", "patronymic");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        assertThatThrownBy(() -> authorService.createAuthor(author.getSurname(), author.getName(), author.getPatronymic()))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Author name is null or empty!");
    }

    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedGenreCount() {
        when(authorRepository.count()).thenReturn(EXPECTED_AUTHOR_COUNT);

        long actualGenreCount = authorService.countAuthors();
        assertThat(actualGenreCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        when(authorRepository.findAll()).thenReturn(List.of(EXPECTED_AUTHOR));
        when(authorMapper.entityToDto(List.of(EXPECTED_AUTHOR))).thenReturn(List.of(EXPECTED_AUTHOR_DTO));

        var actualList = authorService.getAllAuthors();

        assertThat(actualList).usingRecursiveFieldByFieldElementComparator().containsExactly(EXPECTED_AUTHOR_DTO);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(EXPECTED_AUTHOR));
        when(authorMapper.entityToDto(any(Author.class))).thenReturn(EXPECTED_AUTHOR_DTO);

        var actualAuthor = authorService.getAuthorById(EXPECTED_AUTHOR.getId());

        assertThat(actualAuthor)
                .isEqualTo(EXPECTED_AUTHOR_DTO);
    }

    @DisplayName("добавлять автора")
    @Test
    void shouldCreateAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(EXPECTED_AUTHOR);
        when(authorMapper.entityToDto(any(Author.class))).thenReturn(EXPECTED_AUTHOR_DTO);

        var actualAuthor = authorService.createAuthor(
                EXPECTED_AUTHOR.getSurname(),
                EXPECTED_AUTHOR.getName(),
                EXPECTED_AUTHOR.getPatronymic()
        );

        assertThat(actualAuthor)
                .isEqualTo(EXPECTED_AUTHOR_DTO);
    }

    @DisplayName("обновлять автора по id")
    @Test
    void shouldUpdateAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(EXPECTED_AUTHOR);
        when(authorMapper.entityToDto(any(Author.class))).thenReturn(EXPECTED_AUTHOR_DTO);

        var actualAuthor = authorService.updateAuthor(
                EXPECTED_AUTHOR.getId(),
                EXPECTED_AUTHOR.getSurname(),
                EXPECTED_AUTHOR.getName(),
                EXPECTED_AUTHOR.getPatronymic()
        );

        assertThat(actualAuthor)
                .isEqualTo(EXPECTED_AUTHOR_DTO);
    }

    @Test
    @DisplayName("корректно кидать исключение при попытке удалить несуществующего автора")
    void shouldCorrectlyExceptionByDeleteAuthor() {
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.deleteAuthorById(-1))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Author not find with id = " + -1);
    }
}
