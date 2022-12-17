package ru.iashinme.homework04.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework04.dao.QuestionDao;
import ru.iashinme.homework04.domain.Question;

import java.util.List;


@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getQuestionList() {
        return questionDao.findAll();
    }
}
