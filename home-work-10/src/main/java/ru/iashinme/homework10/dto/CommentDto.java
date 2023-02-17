package ru.iashinme.homework10.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.iashinme.homework10.model.Comment;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class CommentDto {
    private long id;
    private LocalDateTime time;
    private String messageComment;
    private BookDto book;

    public Comment toEntity() {
        return new Comment(id, book.toEntity(), time, messageComment);
    }
}
