package ru.iashinme.homework10.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSmallDto {
    private long id;
    private long bookId;
    private String commentMessage;
}
