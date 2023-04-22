package ru.iashinme.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework18.dto.AuthorDto;
import ru.iashinme.homework18.exception.ValidateException;
import ru.iashinme.homework18.mapper.AuthorMapper;
import ru.iashinme.homework18.model.Author;
import ru.iashinme.homework18.repository.AuthorRepository;

import java.util.Collections;
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
    public AuthorDto create(String surname, String name, String patronymic) {
        validate(surname, name);
        var author = new Author(surname, name, patronymic);
        return authorMapper.entityToDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public AuthorDto update(long id, String surname, String name, String patronymic) {
        validate(surname, name);

        var author = authorRepository.findById(id).orElseThrow(
                () -> new ValidateException("Author not find with id = " + id)
        );

        author.setSurname(surname);
        author.setName(name);
        author.setPatronymic(patronymic);

        return authorMapper.entityToDto(authorRepository.save(author));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto findById(long id) {
        return authorRepository.findById(id).map(authorMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Author not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(fallbackMethod = "getAuthors")
    public List<AuthorDto> findAll() {
        return authorMapper.entityToDto(authorRepository.findAll());
    }

    private List<AuthorDto> getAuthors() {
        return Collections.emptyList();
    }
    @Override
    @Transactional
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    private void validate(String authorSurname, String authorName) {
        if (StringUtils.isBlank(authorName))
            throw new ValidateException("Author name is null or empty!");
        if (StringUtils.isBlank(authorSurname))
            throw new ValidateException("Author surname is null or empty!");
    }
}
