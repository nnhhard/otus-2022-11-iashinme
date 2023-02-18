package ru.iashinme.homework11.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.iashinme.homework11.model.Book;

@Data
@Getter
@Builder
public class BookDto {
    private String id;
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
