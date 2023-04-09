package ru.iashinme.homework17.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class CommentWithoutBookDto {
    private long id;
    private LocalDateTime time;
    private String messageComment;
}
