package ru.iashinme.homework17.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework17.dto.*;
import ru.iashinme.homework17.exception.ValidateException;
import ru.iashinme.homework17.mapper.BookMapper;
import ru.iashinme.homework17.model.Author;
import ru.iashinme.homework17.model.Book;
import ru.iashinme.homework17.model.Genre;
import ru.iashinme.homework17.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами должен ")
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookMapper bookMapper;

    private static final Genre EXPECTED_GENRE = new Genre(-1, "GENRE_NAME");
    private static final GenreDto EXPECTED_GENRE_DTO = GenreDto
            .builder()
            .id(EXPECTED_GENRE.getId())
            .name(EXPECTED_GENRE.getName())
            .build();


    private static final Author EXPECTED_AUTHOR = new Author(-1, "surname", "name", "patronymic");
    private static final AuthorDto EXPECTED_AUTHOR_DTO = AuthorDto
            .builder()
            .id(EXPECTED_AUTHOR.getId())
            .surname(EXPECTED_AUTHOR.getSurname())
            .name(EXPECTED_AUTHOR.getName())
            .patronymic(EXPECTED_AUTHOR.getPatronymic())
            .build();


    private static final Book EXPECTED_BOOK = new Book(-1, "Мертвые дущи том №2", EXPECTED_AUTHOR, EXPECTED_GENRE);
    private static final BookDto EXPECTED_BOOK_DTO = BookDto
            .builder()
            .id(EXPECTED_BOOK.getId())
            .name(EXPECTED_BOOK.getName())
            .genre(EXPECTED_GENRE_DTO)
            .author(EXPECTED_AUTHOR_DTO)
            .build();


    private static final long EXPECTED_BOOK_COUNT = 1;

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки наименования книги")
    public void shouldHaveCorrectExceptionForBookName() {
        assertThatThrownBy(() -> bookService.save(new Book(0, "", EXPECTED_AUTHOR, EXPECTED_GENRE)))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Book name is null or empty!");
    }

    @DisplayName("возвращать ожидаемое количество книг")
    @Test
    void shouldReturnExpectedBookCount() {
        when(bookRepository.count()).thenReturn(EXPECTED_BOOK_COUNT);
        long actualBookCount = bookService.countBooks();

        assertThat(actualBookCount)
                .isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        when(bookRepository.findAll()).thenReturn(List.of(EXPECTED_BOOK));
        when(bookMapper.entityToDto(List.of(EXPECTED_BOOK))).thenReturn(List.of(EXPECTED_BOOK_DTO));

        var actualList = bookService.findAll();

        assertThat(actualList).usingRecursiveFieldByFieldElementComparator().containsExactly(EXPECTED_BOOK_DTO);
    }

    @DisplayName("возвращать ожидаемую книгу по его id")
    @Test
    void shouldReturnExpectedBookById() {
        when(bookRepository.findById(EXPECTED_BOOK.getId())).thenReturn(Optional.of(EXPECTED_BOOK));
        when(bookMapper.entityToDto(any(Book.class))).thenReturn(EXPECTED_BOOK_DTO);

        var actualBook = bookService.findById(EXPECTED_BOOK.getId());

        assertThat(actualBook)
                .isEqualTo(EXPECTED_BOOK_DTO);
    }

    @DisplayName("добавлять книгу")
    @Test
    void shouldCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(EXPECTED_BOOK);
        when(genreService.findById(any(Long.class))).thenReturn(EXPECTED_GENRE_DTO);
        when(authorService.findById(any(Long.class))).thenReturn(EXPECTED_AUTHOR_DTO);
        when(bookMapper.entityToDto(any(Book.class))).thenReturn(EXPECTED_BOOK_DTO);

        var actualBook = bookService.save(EXPECTED_BOOK);

        assertThat(actualBook)
                .isEqualTo(EXPECTED_BOOK_DTO);
    }

    @DisplayName("обновлять книгу по id")
    @Test
    void shouldUpdateBook() {
        Book book = new Book(-1, "Мертвые дущи том №2", EXPECTED_AUTHOR, EXPECTED_GENRE);
        BookDto bookDto = BookDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(EXPECTED_GENRE_DTO)
                .author(EXPECTED_AUTHOR_DTO)
                .build();

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(genreService.findById(EXPECTED_GENRE_DTO.getId())).thenReturn(EXPECTED_GENRE_DTO);
        when(authorService.findById(EXPECTED_AUTHOR_DTO.getId())).thenReturn(EXPECTED_AUTHOR_DTO);
        when(bookMapper.entityToDto(any(Book.class))).thenReturn(bookDto);

        var actualBook = bookService.save(book);

        assertThat(actualBook)
                .isEqualTo(bookDto);
    }
}
