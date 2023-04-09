package ru.iashinme.homework17.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iashinme.homework17.dto.BookDto;
import ru.iashinme.homework17.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    public List<BookDto> entityToDto(List<Book> books) {
        return books.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

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
