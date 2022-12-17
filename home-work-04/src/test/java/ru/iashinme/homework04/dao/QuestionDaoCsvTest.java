package ru.iashinme.homework04.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework04.config.ResourceNameProvider;
import ru.iashinme.homework04.domain.Answer;
import ru.iashinme.homework04.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@DisplayName("Class QuestionDaoImpl")
@SpringBootTest(classes = QuestionDaoCsv.class)
public class QuestionDaoCsvTest {

    @Autowired
    private QuestionDao questionDao;

    @MockBean
    private ResourceNameProvider resourceNameProviderMock;

    @Test
    @DisplayName("should have correct exception for reading CSV file")
    public void shouldHaveCorrectExceptionForReadingCsvFile() {
        when(resourceNameProviderMock.getResourceName()).thenReturn("answer-question-test1.csv");
        assertThatThrownBy(() -> questionDao.findAll())
                .isInstanceOf(QuestionsReadingException.class)
                .hasMessageContaining("Exception reading CSV file!");
    }

    @Test
    @DisplayName("should have correct reading empty CSV file")
    public void shouldHaveCorrectReadingEmptyCsvFile() {
        when(resourceNameProviderMock.getResourceName()).thenReturn("answer-question-test-empty.csv");
        assertThat(questionDao.findAll())
                .hasSize(0);
    }

    @Test
    @DisplayName("should have correct reading not empty CSV file")
    public void shouldHaveCorrectReadingNotEmptyCsvFile() {
        List<Question> questionListExpected = getQuestionListWithTestData();
        when(resourceNameProviderMock.getResourceName()).thenReturn("answer-question-test.csv");

        List<Question> questionListActual = questionDao.findAll();

        assertThat(questionListActual)
                .hasSize(questionListExpected.size())
                .isEqualTo(questionListExpected);
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
