package ru.iashin.homework06.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class CommentDto {
    private long id;

    private long bookId;

    private LocalDateTime time;

    private String messageComment;
}
