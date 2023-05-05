package ru.iashinme.filestore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "file_info", schema = "fs")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "guid", unique = true)
    private String guid;
    private String type;

    @Column(name = "data")
    private byte[] dataFile;
}