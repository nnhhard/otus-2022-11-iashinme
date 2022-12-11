package ru.iashinme.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Question {
    private String question;
    private List<Answer> answers;
}
