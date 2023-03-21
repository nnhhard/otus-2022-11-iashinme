package ru.iashinme.homework14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.homework14.model.h2.AuthorSQL;
import ru.iashinme.homework14.model.h2.BookSQL;
import ru.iashinme.homework14.model.h2.GenreSQL;
import ru.iashinme.homework14.model.mongo.Book;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    @Transactional(readOnly = true)
    public BookSQL convert(Book book) {
        final AuthorSQL authorSQL = authorService.findByMongoId(book.getAuthor().getId());
        final GenreSQL genreSQL = genreService.findByMongoId(book.getGenre().getId());

        return new BookSQL(book.getName(), genreSQL, authorSQL);
    }
}
