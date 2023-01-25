package ru.iashinme.homework08.service;

import ru.iashinme.homework08.dto.BookWithAllInfoDto;
import ru.iashinme.homework08.dto.BookWithIdNameGenreDto;

import java.util.List;

public interface BookConverter {

    String bookWithAllInfoDtoToString(BookWithAllInfoDto book);

    String bookWithAllInfoDtoListToString(List<BookWithAllInfoDto> books);

    String bookWithIdNameGenreDtoToString(BookWithIdNameGenreDto book);

    String bookWithIdNameGenreDtoListToString(List<BookWithIdNameGenreDto> books);
}
