package ru.iashinme.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Class QuestionConverterImpl")
public class QuestionConverterImplTest {

    @Test
    @DisplayName("should have correct method questionAnswerToStringWithAnswerIndex")
    public void shouldHaveCorrectMethodQuestionAnswerToStringWithAnswerIndex() {
        var questionConverter = new QuestionConverterImpl();

        var question = getQuestion();
        var stringQuestion= questionConverter.questionAnswerToStringWithAnswerIndex(question);

        assertThat(stringQuestion).isEqualTo("Which of the above is the interface?\n0. List<E>\n1. ArrayList<E>");
    }

    @Test
    @DisplayName("should have correct method questionAnswerToStringWithCorrectAnswer")
    public void shouldHaveCorrectMethodQuestionAnswerToStringWithCorrectAnswer() {
        var questionConverter = new QuestionConverterImpl();

        var question = getQuestion();
        var stringQuestion= questionConverter.questionAnswerToStringWithCorrectAnswer(question);

        assertThat(stringQuestion).isEqualTo("Question: Which of the above is the interface? Right answer: List<E>");
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
