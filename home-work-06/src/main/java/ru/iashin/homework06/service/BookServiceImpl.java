package ru.iashin.homework06.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashin.homework06.dto.AuthorDto;
import ru.iashin.homework06.dto.BookWithAllInfoDto;
import ru.iashin.homework06.dto.GenreDto;
import ru.iashin.homework06.exception.ValidateException;
import ru.iashin.homework06.mapper.BookWithAllInfoMapper;
import ru.iashin.homework06.model.Author;
import ru.iashin.homework06.model.Book;
import ru.iashin.homework06.model.Genre;
import ru.iashin.homework06.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookWithAllInfoMapper bookWithAllInfoMapper;

    @Override
    @Transactional(readOnly = true)
    public long countBooks() {
        return bookRepository.count();
    }

    @Override
    @Transactional
    public BookWithAllInfoDto addAuthorForBook(long id, long authorId) {
        Book book = getBook(id);
        AuthorDto author = authorService.getAuthorById(authorId);

        if(book.getAuthors().stream().filter(a -> a.getId() == authorId).count() > 0) {
            throw new ValidateException("This author has already been added to the book");
        }

        book.getAuthors().add(new Author(author.getId(), author.getSurname(), author.getName(), author.getPatronymic()));
        return bookWithAllInfoMapper.entityToDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookWithAllInfoDto deleteAuthorInBook(long id, long authorId) {
        Book book = getBook(id);
        List<Author> authors = book.getAuthors().stream().filter(a -> a.getId() == authorId).collect(Collectors.toList());
        if (authors.size() == 0) {
            throw new ValidateException(
                    String.format("Author (with authorId = %d) not find in Book (with id = %d)", authorId, id)
            );
        }
        authors.forEach(a -> book.getAuthors().remove(a));

        return bookWithAllInfoMapper.entityToDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookWithAllInfoDto createBook(String bookName, long bookGenreId) {
        validateBookName(bookName);
        GenreDto genre = genreService.getGenreById(bookGenreId);

        return bookWithAllInfoMapper.entityToDto(
                bookRepository.save(
                        new Book(0, bookName, new Genre(genre.getId(), genre.getName()))
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public BookWithAllInfoDto getBookById(long id) {
        return bookRepository.findById(id).map(bookWithAllInfoMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Book not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookWithAllInfoDto> getAllBooks() {
        return bookWithAllInfoMapper.entityToDto(bookRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookWithAllInfoDto updateBook(long id, String bookName, long bookGenreId) {
        validateBookName(bookName);

        var book = bookRepository.findById(id).orElseThrow(
                () -> new ValidateException(String.format("Book not find with id = %d", id))
        );
        var genre = genreService.getGenreById(bookGenreId);

        book.setName(bookName);
        book.setGenre(new Genre(genre.getId(), genre.getName()));

        return bookWithAllInfoMapper.entityToDto(bookRepository.save(book));
    }

    private Book getBook(long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new ValidateException(String.format("Book not find with id = %d", id))
        );
    }

    private void validateBookName(String bookName) {
        if (StringUtils.isBlank(bookName)) {
            throw new ValidateException("Book name is null or empty!");
        }
    }
}
