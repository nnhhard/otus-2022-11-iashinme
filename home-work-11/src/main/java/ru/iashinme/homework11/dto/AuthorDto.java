package ru.iashinme.homework11.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.iashinme.homework11.model.Author;

@Data
@Getter
@Builder
public class AuthorDto {
    private String id;
    private String surname;
    private String name;
    private String patronymic;

    public Author toEntity() {
        return new Author(id, surname, name, patronymic);
    }
}
