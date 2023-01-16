package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.CommentWithBookIdNameGenreDto;
import ru.iashin.homework06.dto.CommentWithoutBookDto;

import java.util.List;

public interface CommentConverter {

    String CommentWithoutBookDtoToString(CommentWithoutBookDto comment);

    String CommentWithoutBookDtoListToString(List<CommentWithoutBookDto> comments);

    String CommentWithBookIdNameGenreDtoToString(CommentWithBookIdNameGenreDto comment);
}
