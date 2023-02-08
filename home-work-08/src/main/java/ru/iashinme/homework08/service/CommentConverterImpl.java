package ru.iashinme.homework08.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework08.dto.CommentDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentConverterImpl implements CommentConverter {

    @Override
    public String commentDtoToString(CommentDto comment) {
        return String.join(" ",
                "Id = " + comment.getId(),
                "MessageComment = " + comment.getMessageComment(),
                "Datetime = " + comment.getTime()
        );
    }

    @Override
    public String commentDtoListToStringWithGroupByBookId(List<CommentDto> comments) {
        if (comments.size() == 0) {
            return "Comment No.";
        }

        var commentsMap = comments
                .stream()
                .collect(Collectors.groupingBy(CommentDto::getBookId));
        return commentsMap
                .keySet()
                .stream()
                .map(key -> "BookId = " + key + "\nComments: \n" + getCommentsWithOutBookId(commentsMap.get(key)))
                .collect(Collectors.joining(";\n"));
    }

    private String getCommentsWithOutBookId(List<CommentDto> comments) {
        return comments.stream().map(c ->
                "CommentId = " + c.getId()
                        + " MessageComment = " + c.getMessageComment()
                        + " Datetime = " + c.getTime()
        ).collect(Collectors.joining("\n"));
    }
}
