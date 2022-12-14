package ru.iashinme.homework03.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework03.dao.QuestionDao;
import ru.iashinme.homework03.domain.Question;

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
