package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.CommentWithBookIdNameGenreDto;
import ru.iashin.homework06.dto.CommentWithoutBookDto;

import java.util.List;

public interface CommentConverter {

    String commentWithoutBookDtoToString(CommentWithoutBookDto comment);

    String commentWithoutBookDtoListToString(List<CommentWithoutBookDto> comments);

    String commentWithBookIdNameGenreDtoToString(CommentWithBookIdNameGenreDto comment);
}
