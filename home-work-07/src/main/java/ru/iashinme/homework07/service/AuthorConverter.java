package ru.iashinme.homework07.service;

import ru.iashinme.homework07.dto.AuthorDto;

import java.util.List;

public interface AuthorConverter {

    String toString(AuthorDto author);

    String toString(List<AuthorDto> authors);
}
