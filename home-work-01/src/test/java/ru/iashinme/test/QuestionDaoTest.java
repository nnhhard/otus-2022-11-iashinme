package ru.iashinme.test;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.iashinme.dao.QuestionDaoImpl;
import java.io.IOException;

@DisplayName("Class QuestionDaoImpl")
public class QuestionDaoTest {

    @Test
    @DisplayName("Should have correct reading empty CSV file")
    public void shouldHaveCorrectReadingEmptyCsvFile() throws IOException, CsvException {
        QuestionDaoImpl questionDao = new QuestionDaoImpl("answer-question-test-empty.csv");
        var questionList = questionDao.findAll();
        Assertions.assertEquals(0, questionList.size());
    }

    @Test
    @DisplayName("Should have correct reading not empty CSV file")
    public void shouldHaveCorrectReadingNotEmptyCsvFile() throws IOException, CsvException {
        QuestionDaoImpl questionDao = new QuestionDaoImpl("answer-question-test.csv");
        var questionList = questionDao.findAll();

        Assertions.assertEquals(2, questionList.size());

        Assertions.assertEquals("Which of the above is the interface?", questionList.get(0).getQuestion());
        Assertions.assertEquals("List<E>", questionList.get(0).getAnswers().get(0).getAnswer());
        Assertions.assertEquals(Boolean.TRUE, questionList.get(0).getAnswers().get(0).getIsTrue());
        Assertions.assertEquals("ArrayList<E>", questionList.get(0).getAnswers().get(1).getAnswer());
        Assertions.assertEquals(Boolean.FALSE, questionList.get(0).getAnswers().get(1).getIsTrue());

        Assertions.assertEquals("What will the following code output: int a = 5; int b = a + 2; System.out.println(a > b ? a : b);?", questionList.get(1).getQuestion());
        Assertions.assertEquals("5", questionList.get(1).getAnswers().get(0).getAnswer());
        Assertions.assertEquals(Boolean.FALSE, questionList.get(1).getAnswers().get(0).getIsTrue());
        Assertions.assertEquals("7", questionList.get(1).getAnswers().get(1).getAnswer());
        Assertions.assertEquals(Boolean.TRUE, questionList.get(1).getAnswers().get(1).getIsTrue());
    }

    @Test
    @DisplayName("Should have correct reading CSV file without answer options")
    public void shouldHaveCorrectReadingCsvFileWithoutAnswerOptions() throws IOException, CsvException {
        QuestionDaoImpl questionDao = new QuestionDaoImpl("answer-question-test-without-answer-options.csv");
        var questionList = questionDao.findAll();
        Assertions.assertEquals(1, questionList.size());
        Assertions.assertEquals("What is your name?", questionList.get(0).getQuestion());
        Assertions.assertEquals(0, questionList.get(0).getAnswers().size());
    }
}
