package ru.iashinme.homework12.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iashinme.homework12.dto.CommentDto;
import ru.iashinme.homework12.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final BookMapper bookMapper;

    public List<CommentDto> entityToDto(List<Comment> comments) {
        return comments.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public CommentDto entityToDto(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .book(bookMapper.entityToDto(comment.getBook()))
                .messageComment(comment.getMessageComment())
                .time(comment.getTime())
                .build();
    }
}
