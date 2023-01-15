package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.AuthorDto;

import java.util.List;

public interface AuthorConverter {
    String toString(AuthorDto author);
    String toString(List<AuthorDto> authors);
}
