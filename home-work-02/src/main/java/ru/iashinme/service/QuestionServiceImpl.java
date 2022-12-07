package ru.iashinme.service;

import org.springframework.stereotype.Service;
import ru.iashinme.dao.QuestionDao;
import ru.iashinme.domain.Question;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<String> getQuestionStringList() {
        return getQuestionList()
                .stream()
                .map(answerQuestionConverter::questionAnswerToStringWithCorrectAnswer).collect(Collectors.toList());
    }

    @Override
    public List<Question> getQuestionList() {
        List<Question> questionList = null;
        try {
            questionList = questionDao.findAll();
        } catch (RuntimeException e) {
            inputOutputService.printMessage(e.getMessage());
        }
        return questionList;
    }
}
