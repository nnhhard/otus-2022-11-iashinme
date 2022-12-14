package ru.iashinme.service;

import ru.iashinme.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionList();
}
