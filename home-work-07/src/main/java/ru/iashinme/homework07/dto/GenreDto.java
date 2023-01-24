package ru.iashinme.homework07.dto;

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
