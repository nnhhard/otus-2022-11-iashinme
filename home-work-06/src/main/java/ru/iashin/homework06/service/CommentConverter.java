package ru.iashin.homework06.service;

import ru.iashin.homework06.dto.CommentDto;

import java.util.List;

public interface CommentConverter {

    String toString(CommentDto comment);
    String toString(List<CommentDto> comments);
}
