package ru.iashinme.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework18.dto.GenreDto;
import ru.iashinme.homework18.exception.ValidateException;
import ru.iashinme.homework18.mapper.GenreMapper;
import ru.iashinme.homework18.model.Genre;
import ru.iashinme.homework18.repository.GenreRepository;

import java.util.Collections;
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
        validate(name);
        var genre = new Genre(name);
        return genreMapper.entityToDto(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public GenreDto update(long id, String name) {
        validate(name);
        var genre = genreRepository.findById(id).orElseThrow(
                () -> new ValidateException("Genre not find with id = " + id)
        );

        genre.setName(name);

        return genreMapper.entityToDto(genreRepository.save(genre));
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
    @HystrixCommand(fallbackMethod = "getGenres")
    public List<GenreDto> findAll() {

        return genreRepository.findAll().stream().map(genreMapper::entityToDto).collect(Collectors.toList());
    }

    private List<GenreDto> getGenres() {
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    private void validate(String genreName) {
        if (StringUtils.isBlank(genreName))
            throw new ValidateException("Genre name is null or empty!");
    }
}
