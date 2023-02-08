package ru.iashinme.homework08.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String name;
    private List<Author> authors;
    private Genre genre;

    public Book(String id, String name, Genre genre) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        authors = new ArrayList<>();
    }

    public Book(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
        authors = new ArrayList<>();
    }
}
