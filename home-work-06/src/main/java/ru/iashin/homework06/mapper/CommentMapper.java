package ru.iashin.homework06.mapper;

import org.springframework.stereotype.Component;
import ru.iashin.homework06.dto.CommentDto;
import ru.iashin.homework06.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    public List<CommentDto> entityToDto(List<Comment> comments) {
        return comments.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public CommentDto entityToDto(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .messageComment(comment.getMessageComment())
                .time(comment.getTime())
                .bookId(comment.getBookId())
                .build();
    }
}
