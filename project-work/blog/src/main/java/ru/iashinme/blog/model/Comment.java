package ru.iashinme.blog.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments", schema = "blog")
@NamedEntityGraph(name = "Comment.Author.Post", attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("post")})
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(targetEntity = Post.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Post post;

    @Column(name = "time")
    private LocalDateTime time;
}
