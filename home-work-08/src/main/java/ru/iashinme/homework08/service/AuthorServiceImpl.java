package ru.iashinme.homework08.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework08.dto.AuthorDto;
import ru.iashinme.homework08.exception.ValidateException;
import ru.iashinme.homework08.mapper.AuthorMapper;
import ru.iashinme.homework08.model.Author;
import ru.iashinme.homework08.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookService bookService;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             AuthorMapper authorMapper,
                             @Lazy BookService bookService) {

        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.bookService = bookService;
    }

    @Override
    @Transactional(readOnly = true)
    public long countAuthors() {
        return authorRepository.count();
    }

    @Override
    @Transactional
    public AuthorDto createAuthor(String authorSurname, String authorName, String authorPatronymic) {
        Author author = getValidatedAuthor(null, authorSurname, authorName, authorPatronymic);
        return authorMapper.entityToDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public AuthorDto updateAuthor(String id, String authorSurname, String authorName, String authorPatronymic) {
        Author author = getValidatedAuthor(id, authorSurname, authorName, authorPatronymic);
        return authorMapper.entityToDto(authorRepository.save(author));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(String id) {
        return authorRepository.findById(id).map(authorMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Author not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors() {
        return authorMapper.entityToDto(authorRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteAuthorById(String id) {
        var author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new ValidateException("Author not find with id = " + id);
        }

        if(bookService.countBooksByAuthor(author.get()) > 0) {
            throw new ValidateException("It is not possible to delete a author, since there are books with this author");
        }

        authorRepository.deleteById(id);
    }

    private Author getValidatedAuthor(String id, String authorSurname, String authorName, String authorPatronymic) {
        if (StringUtils.isBlank(authorName))
            throw new ValidateException("Author name is null or empty!");
        if (StringUtils.isBlank(authorSurname))
            throw new ValidateException("Author surname is null or empty!");
        return new Author(id, authorSurname, authorName, authorPatronymic);
    }
}
