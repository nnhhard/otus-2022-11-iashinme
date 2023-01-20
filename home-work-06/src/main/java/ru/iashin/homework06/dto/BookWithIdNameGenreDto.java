package ru.iashin.homework06.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class BookWithIdNameGenreDto {
    private long id;
    private String name;
    private GenreDto genre;
}
