package ru.iashinme.homework14.model.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    private Genre genre;

    private Author author;

    public Book(String name, Genre genre, Author author) {
        this.author = author;
        this.genre = genre;
        this.name = name;
    }
}
