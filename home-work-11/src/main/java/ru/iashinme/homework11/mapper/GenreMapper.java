package ru.iashinme.homework11.mapper;

import org.springframework.stereotype.Component;
import ru.iashinme.homework11.dto.GenreDto;
import ru.iashinme.homework11.model.Genre;

@Component
public class GenreMapper {

    public GenreDto entityToDto(Genre genre) {
        return GenreDto
                .builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
