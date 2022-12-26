package ru.iashinme.homework05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iashinme.homework05.domain.Book;

@Service
@RequiredArgsConstructor
public class BookConverterImpl implements BookConverter {

    private final AuthorConverter authorConverter;
    private final GenreConverter genreConverter;

    @Override
    public String bookToString(Book book) {
        return String.join("\n",
                "Id = " + book.getId(),
                "Name = " + book.getName(),
                "Author: " + authorConverter.authorToString(book.getAuthor()),
                "Genre: " + genreConverter.genreToString(book.getGenre())
        );
    }
}
