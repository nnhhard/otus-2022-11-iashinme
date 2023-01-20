package ru.iashinme.homework07.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class AuthorDto {
    private long id;
    private String surname;
    private String name;
    private String patronymic;
}
