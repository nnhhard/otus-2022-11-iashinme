package ru.iashinme.homework08.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework08.dto.AuthorDto;
import ru.iashinme.homework08.exception.ValidateException;
import ru.iashinme.homework08.mapper.AuthorMapper;
import ru.iashinme.homework08.model.Author;
import ru.iashinme.homework08.model.Book;
import ru.iashinme.homework08.repository.AuthorRepository;
import ru.iashinme.homework08.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;

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
        authorRepository.save(author);

        List<Book> books = bookRepository.findAllByAuthors_Id(id);
        for (var book : books) {
            book.setAuthors(
                    book.getAuthors()
                            .stream()
                            .filter(a -> !a.getId().equals(id))
                            .collect(Collectors.toList())
            );
            book.getAuthors().add(author);
        }
        bookRepository.saveAll(books);

        return authorMapper.entityToDto(author);
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
        if (bookRepository.existsBookByAuthors_Id(id)) {
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
