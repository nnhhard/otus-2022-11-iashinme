package ru.iashinme.service;

import ru.iashinme.domain.Question;

import java.util.List;

public interface QuestionService {
    void printQuestionList();
    List<Question> getQuestionList();
}
