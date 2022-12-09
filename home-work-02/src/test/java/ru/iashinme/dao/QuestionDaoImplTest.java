package ru.iashinme.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.iashinme.config.AppSettingResourceNameProvider;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Class QuestionDaoImpl")
@ExtendWith(MockitoExtension.class)
public class QuestionDaoImplTest {

    @Mock
    private AppSettingResourceNameProvider appSettingResourceNameProviderMock;

    @InjectMocks
    private QuestionDaoImpl questionDaoImpl;

    @Test
    @DisplayName("should have correct exception for reading CSV file")
    public void shouldHaveCorrectExceptionForReadingCsvFile() {
        when(appSettingResourceNameProviderMock.getResourceName()).thenReturn("answer-question-test1.csv");

        assertThatThrownBy(() -> questionDaoImpl.findAll()).isInstanceOf(ReadCsvFileException.class)
                .hasMessageContaining("Exception reading CSV file!");
    }

    @Test
    @DisplayName("should have correct reading empty CSV file")
    public void shouldHaveCorrectReadingEmptyCsvFile() {
        when(appSettingResourceNameProviderMock.getResourceName()).thenReturn("answer-question-test-empty.csv");
        assertThat(questionDaoImpl.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("should have correct reading not empty CSV file")
    public void shouldHaveCorrectReadingNotEmptyCsvFile() {
        List<Question> questionListExpected = getQuestionListWithTestData();
        when(appSettingResourceNameProviderMock.getResourceName()).thenReturn("answer-question-test.csv");

        List<Question> questionListActual = questionDaoImpl.findAll();

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
