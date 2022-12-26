package ru.iashinme.homework05.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.iashinme.homework05.dao.GenreDao;
import ru.iashinme.homework05.domain.Genre;
import ru.iashinme.homework05.exception.ValidateException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public long createGenre(String genreName) {
        Genre genre = getValidatedGenre(0, genreName);
        return genreDao.insert(genre);
    }

    @Override
    public int updateGenre(long id, String genreName) {
        Genre genre = getValidatedGenre(id, genreName);
        return genreDao.update(genre);
    }

    @Override
    public int countGenres() {
        return genreDao.count();
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }

    @Override
    public int deleteGenreById(long id) {
        return genreDao.deleteById(id);
    }

    private Genre getValidatedGenre(long id, String genreName) {
        if(StringUtils.isBlank(genreName))
            throw new ValidateException("Genre name is null or empty!");
        return new Genre(id, genreName);
    }

}
