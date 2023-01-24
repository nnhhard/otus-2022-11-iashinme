package ru.iashinme.homework07.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class CommentWithBookIdNameGenreDto {
    private long id;
    private LocalDateTime time;
    private String messageComment;
    private BookWithIdNameGenreDto book;
}
