package ru.iashinme.homework07.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iashinme.homework07.dto.BookWithAllInfoDto;
import ru.iashinme.homework07.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookWithAllInfoMapper {

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    public List<BookWithAllInfoDto> entityToDto(List<Book> books) {
        return books.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public BookWithAllInfoDto entityToDto(Book book) {
        return BookWithAllInfoDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .authors(authorMapper.entityToDto(book.getAuthors()))
                .genre(genreMapper.entityToDto(book.getGenre()))
                .build();
    }
}
