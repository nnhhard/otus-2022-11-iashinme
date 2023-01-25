package ru.iashinme.homework08.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class CommentDto {
    private String id;
    private LocalDateTime time;
    private String messageComment;
    private String bookId;
}
