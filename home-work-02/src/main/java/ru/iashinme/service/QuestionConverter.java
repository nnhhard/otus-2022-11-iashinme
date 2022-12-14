package ru.iashinme.service;

import ru.iashinme.domain.Question;

public interface QuestionConverter {
    String questionAnswerToStringWithCorrectAnswer(Question question);
    String questionAnswerToStringWithAnswerIndex(Question question);
}
