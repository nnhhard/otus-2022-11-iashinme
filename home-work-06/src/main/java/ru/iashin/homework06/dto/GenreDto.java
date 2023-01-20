package ru.iashin.homework06.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class GenreDto {
    private long id;
    private String name;
}
