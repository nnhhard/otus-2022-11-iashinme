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

    private String type;

    @Column(name = "file_path")
    private String filePath;
}