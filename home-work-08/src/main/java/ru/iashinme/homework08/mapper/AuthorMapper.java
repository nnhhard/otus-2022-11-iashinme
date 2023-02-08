package ru.iashinme.homework08.mapper;

import org.springframework.stereotype.Component;
import ru.iashinme.homework08.dto.AuthorDto;
import ru.iashinme.homework08.model.Author;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    public List<AuthorDto> entityToDto(List<Author> authors) {
        return authors.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

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
