package ru.iashin.homework06.service;

import org.springframework.stereotype.Service;
import ru.iashin.homework06.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreConverterImpl implements GenreConverter {
    @Override
    public String toString(GenreDto genre) {
        return String.join(" ",
                "Id = " + genre.getId(),
                "Name = " + genre.getName()
        );
    }

    @Override
    public String toString(List<GenreDto> genres) {
        if (genres.size() == 0) {
            return "Genre No.";
        }

        var genresString = genres
                .stream()
                .map(this::toString)
                .collect(Collectors.toList());

        return "Genres:\n    " + String.join(",\n    ", genresString) + ".";
    }
}
