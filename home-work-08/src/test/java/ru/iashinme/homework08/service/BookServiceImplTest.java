package ru.iashinme.homework08.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework08.dto.AuthorDto;
import ru.iashinme.homework08.dto.BookWithAllInfoDto;
import ru.iashinme.homework08.dto.BookWithIdNameGenreDto;
import ru.iashinme.homework08.dto.GenreDto;
import ru.iashinme.homework08.exception.ValidateException;
import ru.iashinme.homework08.mapper.BookWithAllInfoMapper;
import ru.iashinme.homework08.mapper.BookWithIdNameGenreMapper;
import ru.iashinme.homework08.model.Author;
import ru.iashinme.homework08.model.Book;
import ru.iashinme.homework08.model.Genre;
import ru.iashinme.homework08.repository.BookRepository;

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
    private BookWithAllInfoMapper bookWithAllInfoMapper;

    @MockBean
    private BookWithIdNameGenreMapper bookWithIdNameGenreMapper;


    private static final Genre EXPECTED_GENRE = new Genre("-1", "GENRE_NAME");
    private static final GenreDto EXPECTED_GENRE_DTO = GenreDto
            .builder()
            .id(EXPECTED_GENRE.getId())
            .name(EXPECTED_GENRE.getName())
            .build();


    private static final Author EXPECTED_AUTHOR = new Author("-1", "surname", "name", "patronymic");
    private static final AuthorDto EXPECTED_AUTHOR_DTO = AuthorDto
            .builder()
            .id(EXPECTED_AUTHOR.getId())
            .surname(EXPECTED_AUTHOR.getSurname())
            .name(EXPECTED_AUTHOR.getName())
            .patronymic(EXPECTED_AUTHOR.getPatronymic())
            .build();


    private static final Book EXPECTED_BOOK = new Book("-1", "Мертвые дущи том №2", EXPECTED_GENRE);
    private static final BookWithAllInfoDto EXPECTED_BOOK_DTO = BookWithAllInfoDto
            .builder()
            .id(EXPECTED_BOOK.getId())
            .name(EXPECTED_BOOK.getName())
            .genre(EXPECTED_GENRE_DTO)
            .build();

    private static final BookWithIdNameGenreDto EXPECTED_BOOK_ID_NAME_GENRE_DTO = BookWithIdNameGenreDto
            .builder()
            .id(EXPECTED_BOOK.getId())
            .name(EXPECTED_BOOK.getName())
            .genre(EXPECTED_GENRE_DTO)
            .build();


    private static final long EXPECTED_BOOK_COUNT = 1;

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки наименования книги")
    public void shouldHaveCorrectExceptionForBookName() {
        assertThatThrownBy(() -> bookService.createBook("", "-1"))
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
        when(bookWithIdNameGenreMapper.entityToDto(List.of(EXPECTED_BOOK))).thenReturn(List.of(EXPECTED_BOOK_ID_NAME_GENRE_DTO));

        var actualList = bookService.getAllBooks();

        assertThat(actualList).usingRecursiveFieldByFieldElementComparator().containsExactly(EXPECTED_BOOK_ID_NAME_GENRE_DTO);
    }

    @DisplayName("возвращать ожидаемую книгу по его id")
    @Test
    void shouldReturnExpectedBookById() {
        when(bookRepository.findById(EXPECTED_BOOK.getId())).thenReturn(Optional.of(EXPECTED_BOOK));
        when(bookWithAllInfoMapper.entityToDto(any(Book.class))).thenReturn(EXPECTED_BOOK_DTO);

        var actualBook = bookService.getBookById(EXPECTED_BOOK.getId());

        assertThat(actualBook)
                .isEqualTo(EXPECTED_BOOK_DTO);
    }

    @DisplayName("добавлять книгу")
    @Test
    void shouldCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(EXPECTED_BOOK);
        when(genreService.getGenreById(any(String.class))).thenReturn(EXPECTED_GENRE_DTO);
        when(bookWithIdNameGenreMapper.entityToDto(any(Book.class))).thenReturn(EXPECTED_BOOK_ID_NAME_GENRE_DTO);

        var actualBook = bookService.createBook(EXPECTED_BOOK.getName(), EXPECTED_BOOK.getGenre().getId());

        assertThat(actualBook)
                .isEqualTo(EXPECTED_BOOK_ID_NAME_GENRE_DTO);
    }

    @DisplayName("обновлять книгу по id")
    @Test
    void shouldUpdateBook() {
        Book book = new Book("-1", "Мертвые дущи том №2", EXPECTED_GENRE);
        BookWithIdNameGenreDto bookDto = BookWithIdNameGenreDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(EXPECTED_GENRE_DTO)
                .build();

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(genreService.getGenreById(EXPECTED_GENRE_DTO.getId())).thenReturn(EXPECTED_GENRE_DTO);
        when(bookWithIdNameGenreMapper.entityToDto(any(Book.class))).thenReturn(bookDto);

        var actualBook = bookService.updateBook(book.getId(), book.getName(), book.getGenre().getId());

        assertThat(actualBook)
                .isEqualTo(bookDto);
    }

    @Test
    @DisplayName("добавлять автора в книгу")
    void shouldCorrectlyAddAuthor() {
        Book book = new Book("-1", "Мертвые дущи том №2", EXPECTED_GENRE);
        BookWithAllInfoDto bookDto = BookWithAllInfoDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(EXPECTED_GENRE_DTO)
                .build();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(authorService.getAuthorById(EXPECTED_AUTHOR_DTO.getId())).thenReturn(EXPECTED_AUTHOR_DTO);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookWithAllInfoMapper.entityToDto(any(Book.class))).thenReturn(bookDto);

        var actualBook = bookService.addAuthorForBook(book.getId(), EXPECTED_AUTHOR.getId());

        assertThat(actualBook)
                .isEqualTo(bookDto);
    }

    @Test
    @DisplayName("удалять автора из книги")
    void shouldCorrectlyDeleteAuthorInBook() {
        Book book = new Book("-1", "Мертвые дущи том №2", EXPECTED_GENRE);
        book.getAuthors().add(EXPECTED_AUTHOR);
        BookWithAllInfoDto bookDto = BookWithAllInfoDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(EXPECTED_GENRE_DTO)
                .authors(List.of(EXPECTED_AUTHOR_DTO))
                .build();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookWithAllInfoMapper.entityToDto(any(Book.class))).thenReturn(bookDto);

        var actualBook = bookService.deleteAuthorInBook(book.getId(), EXPECTED_AUTHOR.getId());

        assertThat(actualBook)
                .isEqualTo(bookDto);
    }
}
