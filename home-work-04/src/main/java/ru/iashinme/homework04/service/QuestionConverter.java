package ru.iashinme.homework04.service;

import ru.iashinme.homework04.domain.Question;

public interface QuestionConverter {
    String questionAnswerToStringWithCorrectAnswer(Question question);
    String questionAnswerToStringWithAnswerIndex(Question question);
}
