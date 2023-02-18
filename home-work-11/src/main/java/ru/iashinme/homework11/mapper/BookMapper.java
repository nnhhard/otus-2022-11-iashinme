package ru.iashinme.homework11.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iashinme.homework11.dto.BookDto;
import ru.iashinme.homework11.model.Book;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    public BookDto entityToDto(Book book) {
        return BookDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .author(authorMapper.entityToDto(book.getAuthor()))
                .genre(genreMapper.entityToDto(book.getGenre()))
                .build();
    }
}
