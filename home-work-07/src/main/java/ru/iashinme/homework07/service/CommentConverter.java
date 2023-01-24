package ru.iashinme.homework07.service;

import ru.iashinme.homework07.dto.CommentWithBookIdNameGenreDto;
import ru.iashinme.homework07.dto.CommentWithoutBookDto;

import java.util.List;

public interface CommentConverter {

    String commentWithoutBookDtoToString(CommentWithoutBookDto comment);

    String commentWithoutBookDtoListToString(List<CommentWithoutBookDto> comments);

    String commentWithBookIdNameGenreDtoToString(CommentWithBookIdNameGenreDto comment);
}
