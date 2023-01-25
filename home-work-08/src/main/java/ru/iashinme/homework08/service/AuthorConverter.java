package ru.iashinme.homework08.service;

import ru.iashinme.homework08.dto.AuthorDto;

import java.util.List;

public interface AuthorConverter {

    String toString(AuthorDto author);

    String toString(List<AuthorDto> authors);
}
