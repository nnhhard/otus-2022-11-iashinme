package ru.iashinme.service;

import ru.iashinme.dao.QuestionDao;
import ru.iashinme.domain.Question;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final InputOutputService inputOutputService;

    public QuestionServiceImpl(QuestionDao questionDao, InputOutputService inputOutputService) {
        this.questionDao = questionDao;
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void printQuestionList() {
        List<Question> questionList = getQuestionList();
        for (Question question : questionList) {
            String stringAnswers = question.getAnswers().stream()
                    .map(i -> i.getAnswer() + "(" + i.getIsTrue() + ")")
                    .collect(Collectors.joining(", ", "Answer options: ", ""));
            inputOutputService.printMessage(
                    String.join(" ", "Question:", question.getQuestion(), stringAnswers)
            );
        }
    }

    private List<Question> getQuestionList() {
        return questionDao.findAll();
    }
}
