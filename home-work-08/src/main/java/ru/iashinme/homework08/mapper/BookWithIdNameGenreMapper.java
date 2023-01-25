package ru.iashinme.homework08.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iashinme.homework08.dto.BookWithIdNameGenreDto;
import ru.iashinme.homework08.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookWithIdNameGenreMapper {

    private final GenreMapper genreMapper;

    public List<BookWithIdNameGenreDto> entityToDto(List<Book> books) {
        return books.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public BookWithIdNameGenreDto entityToDto(Book book) {
        return BookWithIdNameGenreDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(genreMapper.entityToDto(book.getGenre()))
                .build();
    }
}
