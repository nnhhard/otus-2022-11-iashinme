package ru.iashin.homework06.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iashin.homework06.dto.CommentWithBookIdNameGenreDto;
import ru.iashin.homework06.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentWithBookIdNameGenreMapper {

    private final BookWithIdNameGenreMapper bookWithIdNameGenreMapper;

    public List<CommentWithBookIdNameGenreDto> entityToDto(List<Comment> comments) {
        return comments.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public CommentWithBookIdNameGenreDto entityToDto(Comment comment) {
        return CommentWithBookIdNameGenreDto
                .builder()
                .id(comment.getId())
                .book(bookWithIdNameGenreMapper.entityToDto(comment.getBook()))
                .messageComment(comment.getMessageComment())
                .time(comment.getTime())
                .build();
    }
}
