package ru.iashin.homework06.service;

import org.springframework.stereotype.Service;
import ru.iashin.homework06.dto.CommentDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentConverterImpl implements CommentConverter {
    @Override
    public String toString(CommentDto comment) {
        return String.join(" ",
                "Id = " + comment.getId(),
                "BookId = " + comment.getBookId(),
                "MessageComment = " + comment.getMessageComment(),
                "Datetime = " + comment.getTime()
        );
    }

    @Override
    public String toString(List<CommentDto> comments) {
        if(comments.size() == 0) {
            return "Comment No.";
        }

        var commentsString = comments
                .stream()
                .map(this::toString)
                .collect(Collectors.toList());

        return  "Comments:\n    " + String.join(",\n    ", commentsString) + ".";
    }
}
