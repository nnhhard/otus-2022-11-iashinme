package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.BookWithAllInfoDto;
import ru.iashin.homework06.dto.BookWithIdNameGenreDto;

import java.util.List;

public interface BookConverter {

    String BookWithAllInfoDtoToString(BookWithAllInfoDto book);

    String BookWithAllInfoDtoListToString(List<BookWithAllInfoDto> books);

    String BookWithIdNameGenreDtoToString(BookWithIdNameGenreDto book);

    String BookWithIdNameGenreDtoListToString(List<BookWithIdNameGenreDto> books);
}
