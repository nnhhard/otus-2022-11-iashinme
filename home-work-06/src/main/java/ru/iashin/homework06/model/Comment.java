package ru.iashin.homework06.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "book_id", nullable = false)
    private long bookId;

    @Column(name = "time")
    private LocalDateTime time = now();

    @Column(name = "message_comment")
    private String messageComment;

    public Comment(long bookId, String messageComment) {
        this.bookId = bookId;
        this.messageComment = messageComment;
    }

    public Comment(long id, long bookId, String messageComment) {
        this.id = id;
        this.bookId = bookId;
        this.messageComment = messageComment;
    }
}
