package ru.iashin.homework06.mapper;

import org.springframework.stereotype.Component;
import ru.iashin.homework06.dto.AuthorDto;
import ru.iashin.homework06.model.Author;

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
