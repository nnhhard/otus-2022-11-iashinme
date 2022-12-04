package ru.iashinme.service;

import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;

public interface AnswerQuestionConverter {
    String answerWithCorrectToString(Answer answer);
    String questionAnswerToStringWithCorrectAnswer(Question question);
    String questionAnswerToStringWithAnswerIndex(Question question);
}
