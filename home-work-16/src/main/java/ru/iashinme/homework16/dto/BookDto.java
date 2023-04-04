package ru.iashinme.homework16.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.iashinme.homework16.model.Book;

import java.util.List;

@Data
@Getter
@Builder
public class BookDto {
    private long id;
    private String name;
    private AuthorDto author;
    private GenreDto genre;

    public Book toEntity() {
        return new Book(
                id,
                name,
                author.toEntity(),
                genre.toEntity()
        );
    }
}
