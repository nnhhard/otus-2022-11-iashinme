package ru.iashinme.homework11.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.iashinme.homework11.model.Genre;

@Data
@Getter
@Builder
public class GenreDto {
    private String id;
    private String name;

    public Genre toEntity() {
        return new Genre(id, name);
    }
}
