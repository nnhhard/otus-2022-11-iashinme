package ru.iashinme.homework13.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.iashinme.homework13.model.Genre;

@Data
@Getter
@Builder
public class GenreDto {
    private long id;
    private String name;

    public Genre toEntity() {
        return new Genre(id, name);
    }
}
