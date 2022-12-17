package ru.iashinme.homework04.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework04.domain.Answer;
import ru.iashinme.homework04.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Class QuestionConverterImpl")
@SpringBootTest(classes = QuestionConverterImpl.class)
public class QuestionConverterImplTest {

    @Autowired
    private QuestionConverter questionConverter;

    @MockBean
    private LocalizationService localizationService;

    @Test
    @DisplayName("should have correct method questionAnswerToStringWithAnswerIndex")
    public void shouldHaveCorrectMethodQuestionAnswerToStringWithAnswerIndex() {
        var question = getQuestion();
        var stringQuestion = questionConverter.questionAnswerToStringWithAnswerIndex(question);

        assertThat(stringQuestion).isEqualTo("Which of the above is the interface?\n0. List<E>\n1. ArrayList<E>");
    }

    @Test
    @DisplayName("should have correct method questionAnswerToStringWithCorrectAnswer")
    public void shouldHaveCorrectMethodQuestionAnswerToStringWithCorrectAnswer() {
        var question = getQuestion();
        when(localizationService.getLocalizeMessage(anyString())).thenReturn("foo");

        questionConverter.questionAnswerToStringWithCorrectAnswer(question);

        verify(localizationService, times(1)).getLocalizeMessage(
                "message.question-with-right-answer",
                question.getQuestion(),
                question
                        .getAnswers()
                        .stream()
                        .filter(Answer::isCorrect)
                        .map(Answer::getAnswer)
                        .collect(Collectors.joining(", "))
        );
    }

    private static Question getQuestion() {
        String questionFirst = "Which of the above is the interface?";
        Answer answerFirstForQuestionFirst = new Answer("List<E>", true);
        Answer answerSecondForQuestionFirst = new Answer("ArrayList<E>", false);
        List<Answer> answerListForQuestionFirst = new ArrayList<>();
        answerListForQuestionFirst.add(answerFirstForQuestionFirst);
        answerListForQuestionFirst.add(answerSecondForQuestionFirst);

        return new Question(questionFirst, answerListForQuestionFirst);
    }
}
