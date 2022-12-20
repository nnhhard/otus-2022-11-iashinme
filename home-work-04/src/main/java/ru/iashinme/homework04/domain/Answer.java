package ru.iashinme.homework04.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {
    private String answer;
    private boolean correct;
}
