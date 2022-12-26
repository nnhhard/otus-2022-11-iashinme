package ru.iashinme.homework05.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework05.domain.Author;

@Service
public class AuthorConverterImpl implements AuthorConverter {

    @Override
    public String authorToString(Author author) {
        return  String.join(" ",
                "Id = " + author.getId(),
                "Surname = " + author.getSurname(),
                "Name = " + author.getName(),
                "Patronymic = " + author.getPatronymic()
        );
    }
}
