package ru.iashinme.homework16.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSmallDto {

    private long id;
    private String name;
    private long authorId;
    private long genreId;
}
