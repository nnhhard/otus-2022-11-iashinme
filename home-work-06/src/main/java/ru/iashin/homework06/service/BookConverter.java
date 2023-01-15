package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.BookWithAllInfoDto;

import java.util.List;

public interface BookConverter {
    String toString(BookWithAllInfoDto book);
    String toString(List<BookWithAllInfoDto> books);
}
