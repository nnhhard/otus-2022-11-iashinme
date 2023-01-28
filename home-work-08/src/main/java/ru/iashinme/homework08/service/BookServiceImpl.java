package ru.iashinme.homework08.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework08.dto.BookWithAllInfoDto;
import ru.iashinme.homework08.dto.BookWithIdNameGenreDto;
import ru.iashinme.homework08.exception.ValidateException;
import ru.iashinme.homework08.mapper.BookWithAllInfoMapper;
import ru.iashinme.homework08.mapper.BookWithIdNameGenreMapper;
import ru.iashinme.homework08.model.Author;
import ru.iashinme.homework08.model.Book;
import ru.iashinme.homework08.model.Genre;
import ru.iashinme.homework08.repository.AuthorRepository;
import ru.iashinme.homework08.repository.BookRepository;
import ru.iashinme.homework08.repository.CommentRepository;
import ru.iashinme.homework08.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final BookWithAllInfoMapper bookWithAllInfoMapper;
    private final BookWithIdNameGenreMapper bookWithIdNameGenreMapper;

    @Override
    @Transactional(readOnly = true)
    public long countBooks() {
        return bookRepository.count();
    }

    @Override
    @Transactional
    public BookWithAllInfoDto addAuthorForBook(String id, String authorId) {
        Book book = getBook(id);
        Author author = authorRepository.findById(authorId).orElseThrow(
                () -> new ValidateException("Author not find with authorId = " + authorId)
        );

        if (book.getAuthors().stream().noneMatch(a -> a.getId().equals(authorId))) {
            book.getAuthors().add(author);
            bookRepository.save(book);
        }

        return bookWithAllInfoMapper.entityToDto(book);
    }

    @Override
    @Transactional
    public BookWithAllInfoDto deleteAuthorInBook(String id, String authorId) {
        Book book = getBook(id);
        List<Author> deletedAuthors = book.getAuthors()
                .stream()
                .filter(a -> a.getId().equals(authorId))
                .collect(Collectors.toList());

        if (deletedAuthors.size() > 0) {
            deletedAuthors.forEach(a -> book.getAuthors().remove(a));
            bookRepository.save(book);
        }

        return bookWithAllInfoMapper.entityToDto(book);
    }

    @Override
    @Transactional
    public BookWithIdNameGenreDto createBook(String bookName, String genreId) {
        validateBookName(bookName);
        Genre genre = genreRepository.findById(genreId).orElseThrow(
                () -> new ValidateException("Genre not find with genreId = " + genreId)
        );

        return bookWithIdNameGenreMapper.entityToDto(
                bookRepository.save(new Book(bookName, genre))
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
        commentRepository.deleteAllByBook_Id(id);
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookWithIdNameGenreDto updateBook(String id, String bookName, String genreId) {
        validateBookName(bookName);

        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ValidateException(String.format("Book not find with id = %s", id))
        );

        Genre genre = genreRepository.findById(genreId).orElseThrow(
                () -> new ValidateException("Genre not find with genreId = " + genreId)
        );

        book.setName(bookName);
        book.setGenre(genre);

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
