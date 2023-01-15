package ru.iashin.homework06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iashin.homework06.dto.BookWithAllInfoDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookConverterImpl implements BookConverter {

    private final AuthorConverter authorConverter;
    private final GenreConverter genreConverter;
    private final CommentConverter commentConverter;

    public String toString(BookWithAllInfoDto book) {
        return String.join("\n",
                "Id = " + book.getId(),
                "Name = " + book.getName(),
                "Genre: " + genreConverter.toString(book.getGenre()),
                authorConverter.toString(book.getAuthors()),
                commentConverter.toString(book.getComments())
        );
    }

    @Override
    public String toString(List<BookWithAllInfoDto> books) {
        var booksString = books
                .stream()
                .map(this::toString)
                .collect(Collectors.toList());

        return  "Books:\n" + String.join(";\n", booksString) + ";";
    }
}
