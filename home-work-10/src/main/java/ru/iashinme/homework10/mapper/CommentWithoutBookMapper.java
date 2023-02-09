package ru.iashinme.homework10.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iashinme.homework10.dto.CommentWithoutBookDto;
import ru.iashinme.homework10.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentWithoutBookMapper {

    public List<CommentWithoutBookDto> entityToDto(List<Comment> comments) {
        return comments.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public CommentWithoutBookDto entityToDto(Comment comment) {
        return CommentWithoutBookDto
                .builder()
                .id(comment.getId())
                .messageComment(comment.getMessageComment())
                .time(comment.getTime())
                .build();
    }
}
