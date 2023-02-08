package ru.iashinme.homework08.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class GenreDto {
    private String id;
    private String name;
}
