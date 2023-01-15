package ru.iashin.homework06.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashin.homework06.dto.AuthorDto;
import ru.iashin.homework06.exception.ValidateException;
import ru.iashin.homework06.mapper.AuthorMapper;
import ru.iashin.homework06.model.Author;
import ru.iashin.homework06.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public long countAuthors() {
        return authorRepository.count();
    }

    @Override
    @Transactional
    public AuthorDto createAuthor(String authorSurname, String authorName, String authorPatronymic) {
        Author author = getValidatedAuthor(0, authorSurname, authorName, authorPatronymic);
        return authorMapper.entityToDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public AuthorDto updateAuthor(long id, String authorSurname, String authorName, String authorPatronymic) {
        Author author = getValidatedAuthor(id, authorSurname, authorName, authorPatronymic);
        return authorMapper.entityToDto(authorRepository.save(author));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(long id) {
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
    public void deleteAuthorById(long id) {
        if(authorRepository.findById(id).isEmpty()) {
            throw new ValidateException("Author not find with id = " + id);
        }
        authorRepository.deleteById(id);
    }

    private Author getValidatedAuthor(long id, String authorSurname, String authorName, String authorPatronymic) {
        if(StringUtils.isBlank(authorName))
            throw new ValidateException("Author name is null or empty!");
        if(StringUtils.isBlank(authorSurname))
            throw new ValidateException("Author surname is null or empty!");
        return new Author(id, authorSurname, authorName, authorPatronymic);
    }
}
