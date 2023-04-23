package ru.iashinme.homework18.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments", schema = "homework")
@NamedEntityGraph(
        name = "comment-book-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "book", subgraph = "book-genre-author-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "book-genre-author-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("genre"),
                                @NamedAttributeNode("author")
                        }
                )
        }
)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;

    @Column(name = "time")
    private LocalDateTime time = now();

    @Column(name = "message_comment")
    private String messageComment;

    public Comment(Book book, String messageComment) {
        this.book = book;
        this.messageComment = messageComment;
    }

    public Comment(long id, Book book, String messageComment) {
        this.id = id;
        this.book = book;
        this.messageComment = messageComment;
    }
}
