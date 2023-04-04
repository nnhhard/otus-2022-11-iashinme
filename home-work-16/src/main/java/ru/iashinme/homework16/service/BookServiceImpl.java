package ru.iashinme.homework16.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework16.dto.BookDto;
import ru.iashinme.homework16.exception.ValidateException;
import ru.iashinme.homework16.mapper.BookMapper;
import ru.iashinme.homework16.model.Book;
import ru.iashinme.homework16.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public long countBooks() {
        return bookRepository.count();
    }

    @Override
    @Transactional
    public BookDto save(Book book) {
        validateBookName(book.getName());
        return bookMapper.entityToDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookDto create(String name, long authorId, long genreId) {
        validateBookName(name);

        var author = authorService.findById(authorId);
        var genre = genreService.findById(genreId);
        var bookEntity = new Book(name, genre.toEntity(), author.toEntity());

        return bookMapper.entityToDto(bookRepository.save(bookEntity));
    }

    @Override
    @Transactional
    public BookDto update(long id, String name, long authorId, long genreId) {
        validateBookName(name);

        var bookEntity = bookRepository.findById(id).orElseThrow(
                () -> new ValidateException("Book not find with id = " + id)
        );

        var author = authorService.findById(authorId);
        var genre = genreService.findById(genreId);

        bookEntity.setName(name);
        bookEntity.setAuthor(author.toEntity());
        bookEntity.setGenre(genre.toEntity());

        return bookMapper.entityToDto(bookRepository.save(bookEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(long id) {
        return bookRepository.findById(id).map(bookMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Book not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookMapper.entityToDto(bookRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBook() {
        return bookRepository.existsBy();
    }

    private void validateBookName(String bookName) {
        if (StringUtils.isBlank(bookName)) {
            throw new ValidateException("Book name is null or empty!");
        }
    }
}
