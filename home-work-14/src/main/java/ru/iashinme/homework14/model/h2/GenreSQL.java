package ru.iashinme.homework14.model.h2;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class GenreSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mongo_id")
    private String mongoId;

    public GenreSQL(String name, String mongoId) {
        this.name = name;
        this.mongoId = mongoId;
    }
}
