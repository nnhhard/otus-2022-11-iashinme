package ru.iashin.homework06.service;


import ru.iashin.homework06.dto.GenreDto;

import java.util.List;

public interface GenreConverter {

    String toString(GenreDto genre);

    String toString(List<GenreDto> genres);
}
