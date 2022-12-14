package ru.iashinme.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.iashinme.dao.QuestionDao;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Class QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {
    @Mock
    private QuestionDao questionDaoMock;

    @InjectMocks
    private QuestionServiceImpl questionService;

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
