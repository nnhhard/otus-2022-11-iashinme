package ru.iashinme.homework08.mapper;

import org.springframework.stereotype.Component;
import ru.iashinme.homework08.dto.GenreDto;
import ru.iashinme.homework08.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreMapper {
    public List<GenreDto> entityToDto(List<Genre> genres) {
        return genres.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public GenreDto entityToDto(Genre genre) {
        return GenreDto
                .builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}