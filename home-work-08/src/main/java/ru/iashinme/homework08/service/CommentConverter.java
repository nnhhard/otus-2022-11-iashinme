package ru.iashinme.homework08.service;

import ru.iashinme.homework08.dto.CommentDto;

import java.util.List;

public interface CommentConverter {

    String commentDtoToString(CommentDto comment);

    String commentDtoListToStringWithGroupByBookId(List<CommentDto> comments);
}
