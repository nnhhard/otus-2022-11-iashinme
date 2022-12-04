package ru.iashinme.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.iashinme.Main;
import ru.iashinme.domain.Question;

import java.util.List;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Integration test. Class QuestionDaoImpl")
public class QuestionDaoImplIntegrationTest {

    @Test
    @DisplayName("should have correct reading CSV file")
    public void shouldHaveCorrectReadingCsvFile() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuestionDao questionDao = context.getBean(QuestionDao.class);
        List<Question> questionList = questionDao.findAll();

        assertThat(questionList).hasSize(2);
        assertThat(questionList.get(0).getQuestion()).isEqualTo("Which of the above is the interface?");
        assertThat(questionList.get(0).getAnswers().get(0).getAnswer()).isEqualTo("List<E>");
        assertThat(questionList.get(0).getAnswers().get(1).getAnswer()).isEqualTo("ArrayList<E>");
        assertThat(questionList.get(0).getAnswers().get(0).isCorrect()).isEqualTo(true);
        assertThat(questionList.get(0).getAnswers().get(1).isCorrect()).isEqualTo(false);

        assertThat(questionList.get(1).getQuestion()).isEqualTo(
                "What will the following code output: int a = 5; int b = a + 2; System.out.println(a > b ? a : b);?");
        assertThat(questionList.get(1).getAnswers().get(0).getAnswer()).isEqualTo("5");
        assertThat(questionList.get(1).getAnswers().get(1).getAnswer()).isEqualTo("7");
        assertThat(questionList.get(1).getAnswers().get(0).isCorrect()).isEqualTo(false);
        assertThat(questionList.get(1).getAnswers().get(1).isCorrect()).isEqualTo(true);
    }
}
