package ru.iashinme.homework08.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@Builder
public class BookWithAllInfoDto {
    private String id;
    private String name;
    private List<AuthorDto> authors;
    private GenreDto genre;
}
