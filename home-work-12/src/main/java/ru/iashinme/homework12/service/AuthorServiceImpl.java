package ru.iashinme.homework12.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework12.dto.AuthorDto;
import ru.iashinme.homework12.exception.ValidateException;
import ru.iashinme.homework12.mapper.AuthorMapper;
import ru.iashinme.homework12.model.Author;
import ru.iashinme.homework12.repository.AuthorRepository;

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
        return save(0, surname, name, patronymic);
    }

    @Override
    @Transactional
    public AuthorDto update(long id, String surname, String name, String patronymic) {
        return save(id, surname, name, patronymic);
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
    public List<AuthorDto> findAll() {
        return authorMapper.entityToDto(authorRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    private AuthorDto save(long id, String authorSurname, String authorName, String authorPatronymic) {
        if (StringUtils.isBlank(authorName))
            throw new ValidateException("Author name is null or empty!");
        if (StringUtils.isBlank(authorSurname))
            throw new ValidateException("Author surname is null or empty!");

        var author = new Author(id, authorSurname, authorName, authorPatronymic);

        return authorMapper.entityToDto(authorRepository.save(author));
    }
}
