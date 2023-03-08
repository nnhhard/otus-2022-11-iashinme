package ru.iashinme.homework12.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.iashinme.homework12.model.Author;

@Data
@Getter
@Builder
public class AuthorDto {
    private long id;
    private String surname;
    private String name;
    private String patronymic;

    public Author toEntity() {
        return new Author(id, surname, name, patronymic);
    }
}
