package ru.iashinme.homework08.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework08.dto.AuthorDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorConverterImpl implements AuthorConverter {

    @Override
    public String toString(AuthorDto author) {
        return String.join(" ",
                "Id = " + author.getId(),
                "Surname = " + author.getSurname(),
                "Name = " + author.getName(),
                "Patronymic = " + author.getPatronymic()
        );
    }

    @Override
    public String toString(List<AuthorDto> authors) {
        if (authors.size() == 0) {
            return "There are no authors.";
        }

        var authorsString = authors
                .stream()
                .map(this::toString)
                .collect(Collectors.toList());

        return "Authors:\n    " + String.join(",\n    ", authorsString) + ".";
    }
}
