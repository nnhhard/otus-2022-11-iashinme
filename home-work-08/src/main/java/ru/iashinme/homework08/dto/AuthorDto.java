package ru.iashinme.homework08.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.iashinme.homework08.model.Book;

import java.util.List;

@Data
@Getter
@Builder
public class AuthorDto {
    private String id;
    private String surname;
    private String name;
    private String patronymic;
}
