package ru.iashinme.homework08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iashinme.homework08.dto.BookWithAllInfoDto;
import ru.iashinme.homework08.dto.BookWithIdNameGenreDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookConverterImpl implements BookConverter {

    private final AuthorConverter authorConverter;
    private final GenreConverter genreConverter;

    @Override
    public String bookWithAllInfoDtoToString(BookWithAllInfoDto book) {
        return String.join("\n",
                "Id = " + book.getId(),
                "Name = " + book.getName(),
                "Genre: " + genreConverter.toString(book.getGenre()),
                authorConverter.toString(book.getAuthors())
        );
    }

    @Override
    public String bookWithAllInfoDtoListToString(List<BookWithAllInfoDto> books) {
        var booksString = books
                .stream()
                .map(this::bookWithAllInfoDtoToString)
                .collect(Collectors.toList());

        return "Books:\n" + String.join(";\n", booksString) + ";";
    }

    @Override
    public String bookWithIdNameGenreDtoToString(BookWithIdNameGenreDto book) {
        return String.join("\n",
                "Id = " + book.getId(),
                "Name = " + book.getName(),
                "Genre: " + genreConverter.toString(book.getGenre())
        );
    }

    @Override
    public String bookWithIdNameGenreDtoListToString(List<BookWithIdNameGenreDto> books) {
        var booksString = books
                .stream()
                .map(this::bookWithIdNameGenreDtoToString)
                .collect(Collectors.toList());

        return "Books:\n" + String.join(";\n", booksString) + ";";
    }
}
