package ru.iashinme.homework14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework14.model.mongo.Author;
import ru.iashinme.homework14.model.h2.AuthorSQL;
import ru.iashinme.homework14.repository.h2.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public AuthorSQL convert(Author author) {
        return authorRepository.save(
                new AuthorSQL(author.getName(), author.getId())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorSQL findByMongoId(String mongoId) {
        return authorRepository.findByMongoId(mongoId);
    }
}
