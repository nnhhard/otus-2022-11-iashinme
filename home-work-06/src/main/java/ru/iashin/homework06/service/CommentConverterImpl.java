package ru.iashin.homework06.service;

import org.springframework.stereotype.Service;
import ru.iashin.homework06.dto.CommentWithBookIdNameGenreDto;
import ru.iashin.homework06.dto.CommentWithoutBookDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentConverterImpl implements CommentConverter {
    @Override
    public String CommentWithoutBookDtoToString(CommentWithoutBookDto comment) {
        return String.join(" ",
                "Id = " + comment.getId(),
                "MessageComment = " + comment.getMessageComment(),
                "Datetime = " + comment.getTime()
        );
    }

    @Override
    public String CommentWithoutBookDtoListToString(List<CommentWithoutBookDto> comments) {
        if (comments.size() == 0) {
            return "Comment No.";
        }

        var commentsString = comments
                .stream()
                .map(this::CommentWithoutBookDtoToString)
                .collect(Collectors.toList());

        return "Comments:\n    " + String.join(",\n    ", commentsString) + ".";
    }

    @Override
    public String CommentWithBookIdNameGenreDtoToString(CommentWithBookIdNameGenreDto comment) {
        return String.join(" ",
                "CommentId = " + comment.getId(),
                "BookId = " + comment.getBook().getId(),
                "BookName = " + comment.getBook().getName(),
                "GenreId = " + comment.getBook().getGenre().getId(),
                "GenreName = " + comment.getBook().getGenre().getName(),
                "MessageComment = " + comment.getMessageComment(),
                "Datetime = " + comment.getTime()
        );
    }
}
