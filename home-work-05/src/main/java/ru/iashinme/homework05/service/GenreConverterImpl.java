package ru.iashinme.homework05.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework05.domain.Genre;

@Service
public class GenreConverterImpl implements GenreConverter {
    @Override
    public String genreToString(Genre genre) {
        return String.join(" ",
                "Id = " + genre.getId(),
                "Name = " + genre.getName()
        );
    }
}
