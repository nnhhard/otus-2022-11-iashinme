package ru.iashinme.service;

import org.springframework.stereotype.Service;
import ru.iashinme.dao.QuestionDao;
import ru.iashinme.domain.Question;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final InputOutputService inputOutputService;
    private final AnswerQuestionConverter answerQuestionConverter;

    public QuestionServiceImpl(
            QuestionDao questionDao,
            InputOutputService inputOutputService,
            AnswerQuestionConverter answerQuestionConverter) {
        this.questionDao = questionDao;
        this.inputOutputService = inputOutputService;
        this.answerQuestionConverter = answerQuestionConverter;
    }

    @Override
    public void printQuestionList() {
        try {
            getQuestionList().forEach(question -> inputOutputService.printMessage(
                    answerQuestionConverter.questionAnswerToStringWithCorrectAnswer(question))
            );

        } catch (RuntimeException e) {
            inputOutputService.printMessage(e.getMessage());
        }
    }

    @Override
    public List<Question> getQuestionList() {
        return questionDao.findAll();
    }
}
