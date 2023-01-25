package ru.iashinme.homework08.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.stringtemplate.v4.ST;

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
    private String bookId;
    private LocalDateTime time = now();
    private String messageComment;

    public Comment(String bookId, String messageComment) {
        this.bookId = bookId;
        this.messageComment = messageComment;
    }

    public Comment(String id, String bookId, String messageComment) {
        this.id = id;
        this.bookId = bookId;
        this.messageComment = messageComment;
    }
}
