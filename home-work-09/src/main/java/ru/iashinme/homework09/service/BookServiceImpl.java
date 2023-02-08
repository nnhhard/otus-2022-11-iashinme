package ru.iashinme.homework09.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework09.dto.AuthorDto;
import ru.iashinme.homework09.dto.BookDto;
import ru.iashinme.homework09.dto.GenreDto;
import ru.iashinme.homework09.exception.ValidateException;
import ru.iashinme.homework09.mapper.BookMapper;
import ru.iashinme.homework09.model.Book;
import ru.iashinme.homework09.repository.BookRepository;

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

    private void validateBookName(String bookName) {
        if (StringUtils.isBlank(bookName)) {
            throw new ValidateException("Book name is null or empty!");
        }
    }
}
