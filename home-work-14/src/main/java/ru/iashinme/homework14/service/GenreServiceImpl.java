package ru.iashinme.homework14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework14.model.mongo.Genre;
import ru.iashinme.homework14.model.h2.GenreSQL;
import ru.iashinme.homework14.repository.h2.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    final GenreRepository genreRepository;

    @Override
    @Transactional
    public GenreSQL convert(Genre genre) {
        return genreRepository.save(
                new GenreSQL(genre.getName(), genre.getId())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public GenreSQL findByMongoId(String id) {
        return genreRepository.findByMongoId(id);
    }
}
