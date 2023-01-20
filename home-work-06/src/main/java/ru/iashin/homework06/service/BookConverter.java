package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.BookWithAllInfoDto;
import ru.iashin.homework06.dto.BookWithIdNameGenreDto;

import java.util.List;

public interface BookConverter {

    String bookWithAllInfoDtoToString(BookWithAllInfoDto book);

    String bookWithAllInfoDtoListToString(List<BookWithAllInfoDto> books);

    String bookWithIdNameGenreDtoToString(BookWithIdNameGenreDto book);

    String bookWithIdNameGenreDtoListToString(List<BookWithIdNameGenreDto> books);
}
