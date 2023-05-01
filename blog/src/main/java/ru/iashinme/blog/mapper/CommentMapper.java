package ru.iashinme.blog.mapper;

import org.springframework.stereotype.Component;
import ru.iashinme.blog.dto.CommentDto;
import ru.iashinme.blog.model.Comment;

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
                .text(comment.getText())
                .time(comment.getTime())
                .authorFullName(comment.getAuthor().getFullName())
                .build();
    }
}
