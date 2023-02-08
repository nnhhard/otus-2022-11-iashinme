package ru.iashinme.homework09.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework09.dto.GenreDto;
import ru.iashinme.homework09.exception.ValidateException;
import ru.iashinme.homework09.mapper.GenreMapper;
import ru.iashinme.homework09.model.Genre;
import ru.iashinme.homework09.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public GenreDto create(String name) {
        return save(0, name);
    }

    @Override
    @Transactional
    public GenreDto update(long id, String name) {
        return save(id, name);
    }

    @Override
    @Transactional(readOnly = true)
    public long counts() {
        return genreRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public GenreDto findById(long id) {
        return genreRepository.findById(id).map(genreMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Genre not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    private GenreDto save(long id, String genreName) {
        if (StringUtils.isBlank(genreName))
            throw new ValidateException("Genre name is null or empty!");

        var genre = new Genre(id, genreName);

        return genreMapper.entityToDto(genreRepository.save(genre));
    }
}
