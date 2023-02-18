package ru.iashinme.homework11.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @DBRef
    private Book book;

    private LocalDateTime time = now();
    private String messageComment;

    public Comment(Book book, String messageComment) {
        this.book = book;
        this.messageComment = messageComment;
    }

    public Comment(String id, Book book, String messageComment) {
        this.id = id;
        this.book = book;
        this.messageComment = messageComment;
    }
}
