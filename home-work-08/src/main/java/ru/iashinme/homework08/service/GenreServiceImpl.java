package ru.iashinme.homework08.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework08.dto.GenreDto;
import ru.iashinme.homework08.exception.ValidateException;
import ru.iashinme.homework08.mapper.GenreMapper;
import ru.iashinme.homework08.model.Book;
import ru.iashinme.homework08.model.Genre;
import ru.iashinme.homework08.repository.BookRepository;
import ru.iashinme.homework08.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public GenreDto createGenre(String genreName) {
        Genre genre = getValidatedGenre(null, genreName);
        return genreMapper.entityToDto(genreRepository.save(genre));
    }

    @Override
    @Transactional
    public GenreDto updateGenre(String id, String genreName) {
        Genre genre = getValidatedGenre(id, genreName);
        genreRepository.save(genre);

        List<Book> books = bookRepository.findAllByGenre_Id(id);

        books.forEach(
                b -> b.setGenre(genre)
        );

        bookRepository.saveAll(books);
        return genreMapper.entityToDto(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public long countGenres() {
        return genreRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public GenreDto getGenreById(String id) {
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
    public void deleteGenreById(String id) {
        if (bookRepository.existsBookByGenre_Id(id)) {
            throw new ValidateException("It is not possible to delete a genre, since there are books with this genre");
        }

        genreRepository.deleteById(id);
    }

    private Genre getValidatedGenre(String id, String genreName) {
        if (StringUtils.isBlank(genreName))
            throw new ValidateException("Genre name is null or empty!");
        return new Genre(id, genreName);
    }
}
