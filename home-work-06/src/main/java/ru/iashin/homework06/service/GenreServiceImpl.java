package ru.iashin.homework06.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashin.homework06.dto.GenreDto;
import ru.iashin.homework06.exception.ValidateException;
import ru.iashin.homework06.mapper.GenreMapper;
import ru.iashin.homework06.model.Genre;
import ru.iashin.homework06.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public GenreDto createGenre(String genreName) {
        Genre genre = getValidatedGenre(0, genreName);
        return genreMapper.entityToDto(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public GenreDto updateGenre(long id, String genreName) {
        Genre genre = getValidatedGenre(id, genreName);
        return genreMapper.entityToDto(genreRepository.save(genre));
    }

    @Override
    @Transactional(readOnly = true)
    public long countGenres() {
        return genreRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public GenreDto getGenreById(long id) {
        return genreRepository.findById(id).map(genreMapper::entityToDto).orElseThrow(
                () -> new ValidateException("Genre not find with id = " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(genreMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteGenreById(long id) {
        if (genreRepository.findById(id).isEmpty()) {
            throw new ValidateException("Genre not find with id = " + id);
        }
        genreRepository.deleteById(id);
    }

    private Genre getValidatedGenre(long id, String genreName) {
        if (StringUtils.isBlank(genreName))
            throw new ValidateException("Genre name is null or empty!");
        return new Genre(id, genreName);
    }
}
