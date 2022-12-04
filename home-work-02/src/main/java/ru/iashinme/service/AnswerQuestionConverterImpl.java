package ru.iashinme.service;

import org.springframework.stereotype.Service;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerQuestionConverterImpl implements AnswerQuestionConverter {

    @Override
    public String answerWithCorrectToString(Answer answer) {
        return answer.getAnswer() + "(" + answer.isCorrect() + ")";
    }

    @Override
    public String questionAnswerToStringWithCorrectAnswer(Question question) {
        return String.join(
                " ",
                "Question:",
                question.getQuestion(),
                question.getAnswers().stream()
                        .map(this::answerWithCorrectToString)
                        .collect(Collectors.joining(", ", "Answer options: ", ""))
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
