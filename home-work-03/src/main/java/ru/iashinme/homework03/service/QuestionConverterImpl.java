package ru.iashinme.homework03.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework03.domain.Answer;
import ru.iashinme.homework03.domain.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionConverterImpl implements QuestionConverter {
    private final LocalizationService localizationService;

    public QuestionConverterImpl(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @Override
    public String questionAnswerToStringWithCorrectAnswer(Question question) {
        var questionString = question.getQuestion();
        var rightAnswerString = question.getAnswers().stream()
                .filter(Answer::isCorrect)
                .map(Answer::getAnswer)
                .collect(Collectors.joining(", "));

        return localizationService.getLocalizeMessage(
                "message.question-with-right-answer",
                questionString,
                rightAnswerString
        );
    }

    @Override
    public String questionAnswerToStringWithAnswerIndex(Question question) {
        List<String> questionAnswerStringWithAnswerIndex = new ArrayList<>();
        questionAnswerStringWithAnswerIndex.add(question.getQuestion());

        for (int answerIndex = 0; answerIndex < question.getAnswers().size(); answerIndex++) {
            questionAnswerStringWithAnswerIndex.add((answerIndex) + ". " + question.getAnswers().get(answerIndex).getAnswer());
        }
        return String.join("\n", questionAnswerStringWithAnswerIndex);
    }
}
