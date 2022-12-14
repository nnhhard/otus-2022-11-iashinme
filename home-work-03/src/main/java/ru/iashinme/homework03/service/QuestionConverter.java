package ru.iashinme.homework03.service;

import ru.iashinme.homework03.domain.Question;

public interface QuestionConverter {
    String questionAnswerToStringWithCorrectAnswer(Question question);
    String questionAnswerToStringWithAnswerIndex(Question question);
}
