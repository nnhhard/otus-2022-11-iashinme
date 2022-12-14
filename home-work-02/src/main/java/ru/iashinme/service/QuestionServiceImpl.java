package ru.iashinme.service;

import org.springframework.stereotype.Service;
import ru.iashinme.dao.QuestionDao;
import ru.iashinme.domain.Question;

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
