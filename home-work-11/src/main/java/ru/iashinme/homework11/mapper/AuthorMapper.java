package ru.iashinme.homework11.mapper;

import org.springframework.stereotype.Component;
import ru.iashinme.homework11.dto.AuthorDto;
import ru.iashinme.homework11.model.Author;

@Component
public class AuthorMapper {

    public AuthorDto entityToDto(Author author) {
        return AuthorDto
                .builder()
                .id(author.getId())
                .name(author.getName())
                .patronymic(author.getPatronymic())
                .surname(author.getSurname())
                .build();
    }
}
