package ru.iashinme.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Class QuestionDaoImpl")
public class QuestionDaoImplTest {

    @Test
    @DisplayName("should have correct exception for reading CSV file")
    public void shouldHaveCorrectExceptionForReadingCsvFile() {
        QuestionDaoImpl questionDao = new QuestionDaoImpl("answer-question-test1.csv");
        assertThatThrownBy(() -> {
            List<Question> questionList = questionDao.findAll();
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Exception reading CSV file!");
    }

    @Test
    @DisplayName("should have correct reading empty CSV file")
    public void shouldHaveCorrectReadingEmptyCsvFile() {
        QuestionDaoImpl questionDao = new QuestionDaoImpl("answer-question-test-empty.csv");
        assertThat(questionDao.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("should have correct reading not empty CSV file")
    public void shouldHaveCorrectReadingNotEmptyCsvFile() {
        List<Question> questionListExpected = getQuestionListWithTestData();

        QuestionDaoImpl questionDao = new QuestionDaoImpl("answer-question-test.csv");
        List<Question> questionListActual = questionDao.findAll();

        assertThat(questionListActual).hasSize(questionListExpected.size());

        for (int questionIndex = 0; questionIndex < questionListExpected.size(); questionIndex++) {
            assertThat(questionListActual.get(questionIndex).getQuestion()).isEqualTo(questionListExpected.get(questionIndex).getQuestion());
            assertThat(questionListActual.get(questionIndex).getAnswers()).hasSize(questionListExpected.get(questionIndex).getAnswers().size());

            for (int answerIndex = 0; answerIndex < questionListExpected.get(questionIndex).getAnswers().size(); answerIndex++) {
                assertThat(questionListActual.get(questionIndex).getAnswers().get(answerIndex).getAnswer())
                        .isEqualTo(questionListExpected.get(questionIndex).getAnswers().get(answerIndex).getAnswer());

                assertThat(questionListActual.get(questionIndex).getAnswers().get(answerIndex).isCorrect())
                        .isEqualTo(questionListExpected.get(questionIndex).getAnswers().get(answerIndex).isCorrect());
            }
        }
    }

    private static List<Question> getQuestionListWithTestData() {
        List<Question> questionList = new ArrayList<>();

        String questionFirst = "Which of the above is the interface?";
        Answer answerFirstForQuestionFirst = new Answer("List<E>", true);
        Answer answerSecondForQuestionFirst = new Answer("ArrayList<E>", false);
        List<Answer> answerListForQuestionFirst = new ArrayList<>();
        answerListForQuestionFirst.add(answerFirstForQuestionFirst);
        answerListForQuestionFirst.add(answerSecondForQuestionFirst);

        questionList.add(new Question(questionFirst, answerListForQuestionFirst));

        String questionSecond
                = "What will the following code output: int a = 5; int b = a + 2; System.out.println(a > b ? a : b);?";
        Answer answerFirstForQuestionSecond = new Answer("5", false);
        Answer answerSecondForQuestionSecond = new Answer("7", true);
        List<Answer> answerListForQuestionSecond = new ArrayList<>();
        answerListForQuestionSecond.add(answerFirstForQuestionSecond);
        answerListForQuestionSecond.add(answerSecondForQuestionSecond);

        questionList.add(new Question(questionSecond, answerListForQuestionSecond));
        return questionList;
    }
}
