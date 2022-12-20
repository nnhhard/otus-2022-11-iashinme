package ru.iashinme.homework04.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Question {
    private String question;
    private List<Answer> answers;
}
