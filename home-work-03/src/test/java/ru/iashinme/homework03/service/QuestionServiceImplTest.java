package ru.iashinme.homework03.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.homework03.dao.QuestionDao;
import ru.iashinme.homework03.domain.Answer;
import ru.iashinme.homework03.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Class QuestionServiceImpl")
@SpringBootTest(classes = QuestionServiceImpl.class)
public class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;

    @MockBean
    private QuestionDao questionDaoMock;

    @Test
    @DisplayName("should have correct method getQuestionList")
    public void shouldHaveCorrectMethodGetQuestionList() {
        var questions = getQuestionListWithTestData();

        when(questionDaoMock.findAll()).thenReturn(questions);

        var questionsActual = questionService.getQuestionList();

        assertThat(questionsActual).hasSize(questions.size())
                .isEqualTo(questions);
    }

    private static List<Question> getQuestionListWithTestData() {
        List<Question> questions = new ArrayList<>();

        String questionFirst = "Which of the above is the interface?";
        Answer answerFirstForQuestionFirst = new Answer("List<E>", true);
        Answer answerSecondForQuestionFirst = new Answer("ArrayList<E>", false);
        List<Answer> answerListForQuestionFirst = new ArrayList<>();
        answerListForQuestionFirst.add(answerFirstForQuestionFirst);
        answerListForQuestionFirst.add(answerSecondForQuestionFirst);

        questions.add(new Question(questionFirst, answerListForQuestionFirst));

        String questionSecond
                = "What will the following code output: int a = 5; int b = a + 2; System.out.println(a > b ? a : b);?";
        Answer answerFirstForQuestionSecond = new Answer("5", false);
        Answer answerSecondForQuestionSecond = new Answer("7", true);
        List<Answer> answerListForQuestionSecond = new ArrayList<>();
        answerListForQuestionSecond.add(answerFirstForQuestionSecond);
        answerListForQuestionSecond.add(answerSecondForQuestionSecond);

        questions.add(new Question(questionSecond, answerListForQuestionSecond));

        return questions;
    }
}

