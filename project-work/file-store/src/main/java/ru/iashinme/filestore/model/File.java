package ru.iashinme.filestore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "file", schema = "fs")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Column(name = "data")
    private byte[] dataFile;
}