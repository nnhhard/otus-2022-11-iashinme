package ru.iashinme.homework11.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iashinme.homework11.dto.CommentDto;
import ru.iashinme.homework11.model.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public CommentDto entityToDto(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .messageComment(comment.getMessageComment())
                .time(comment.getTime())
                .bookId(comment.getBook().getId())
                .build();
    }
}
