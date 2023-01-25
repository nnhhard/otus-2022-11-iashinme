package ru.iashinme.homework08.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final BookWithAllInfoMapper bookWithAllInfoMapper;
    private final BookWithIdNameGenreMapper bookWithIdNameGenreMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorService authorService,
                           GenreService genreService,
                           @Lazy CommentService commentService,
                           BookWithAllInfoMapper bookWithAllInfoMapper,
                           BookWithIdNameGenreMapper bookWithIdNameGenreMapper) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
        this.bookWithAllInfoMapper = bookWithAllInfoMapper;
        this.bookWithIdNameGenreMapper = bookWithIdNameGenreMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public long countBooks() {
        return bookRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countBooksByGenreId(String genreId) {
        return bookRepository.countAllByGenre_id(genreId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countBooksByAuthor(Author author) {
        return bookRepository.countAllByAuthors(author);
    }

    @Override
    @Transactional
    public BookWithAllInfoDto addAuthorForBook(String id, String authorId) {
        Book book = getBook(id);
        AuthorDto author = authorService.getAuthorById(authorId);

        if (book.getAuthors().stream().anyMatch(a -> a.getId().equals(authorId))) {
            throw new ValidateException("This author has already been added to the book");
        }

        book.getAuthors().add(new Author(author.getId(), author.getSurname(), author.getName(), author.getPatronymic()));
        return bookWithAllInfoMapper.entityToDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookWithAllInfoDto deleteAuthorInBook(String id, String authorId) {
        Book book = getBook(id);
        List<Author> authors = book.getAuthors().stream().filter(a -> a.getId().equals(authorId)).collect(Collectors.toList());
        if (authors.size() == 0) {
            throw new ValidateException(
                    String.format("Author (with authorId = %s) not find in Book (with id = %s)", authorId, id)
            );
        }
        authors.forEach(a -> book.getAuthors().remove(a));

        return bookWithAllInfoMapper.entityToDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookWithIdNameGenreDto createBook(String bookName, String bookGenreId) {
        validateBookName(bookName);
        GenreDto genre = genreService.getGenreById(bookGenreId);

        return bookWithIdNameGenreMapper.entityToDto(
                bookRepository.save(
                        new Book(null, bookName, new Genre(genre.getId(), genre.getName()))
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public BookWithAllInfoDto getBookById(String id) {
        return bookRepository.findById(id).map(bookWithAllInfoMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Book not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookWithIdNameGenreDto> getAllBooks() {
        return bookWithIdNameGenreMapper.entityToDto(bookRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteBookById(String id) {
        commentService.deleteAllByBookId(id);
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookWithIdNameGenreDto updateBook(String id, String bookName, String bookGenreId) {
        validateBookName(bookName);

        var book = bookRepository.findById(id).orElseThrow(
                () -> new ValidateException(String.format("Book not find with id = %s", id))
        );
        var genre = genreService.getGenreById(bookGenreId);

        book.setName(bookName);
        book.setGenre(new Genre(genre.getId(), genre.getName()));

        return bookWithIdNameGenreMapper.entityToDto(bookRepository.save(book));
    }

    private Book getBook(String id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new ValidateException(String.format("Book not find with id = %s", id))
        );
    }

    private void validateBookName(String bookName) {
        if (StringUtils.isBlank(bookName)) {
            throw new ValidateException("Book name is null or empty!");
        }
    }
}
