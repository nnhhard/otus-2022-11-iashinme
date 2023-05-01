package ru.iashinme.blog.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts", schema = "blog")
@NamedEntityGraph(name = "Post.Author.Technology", attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("technology")})
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(targetEntity = Technology.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Technology technology;
}