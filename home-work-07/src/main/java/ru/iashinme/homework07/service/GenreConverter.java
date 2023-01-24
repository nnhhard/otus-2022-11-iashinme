package ru.iashinme.homework07.service;


import ru.iashinme.homework07.dto.GenreDto;

import java.util.List;

public interface GenreConverter {

    String toString(GenreDto genre);

    String toString(List<GenreDto> genres);
}
