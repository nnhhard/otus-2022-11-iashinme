package ru.iashinme.homework08.service;


import ru.iashinme.homework08.dto.GenreDto;

import java.util.List;

public interface GenreConverter {

    String toString(GenreDto genre);

    String toString(List<GenreDto> genres);
}
