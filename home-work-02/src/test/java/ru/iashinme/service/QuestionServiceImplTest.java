package ru.iashinme.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.iashinme.dao.QuestionDao;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Class QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {
    @Mock
    private QuestionDao questionDaoMock;

    @Mock
    private AnswerQuestionConverter answerQuestionConverterMock;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    @DisplayName("should have correct print string")
    public void shouldHaveCorrectFormatPrintString() {
        var mapStringQuestion = getQuestionListWithTestData();

        when(questionDaoMock.findAll()).thenReturn(new ArrayList<>(mapStringQuestion.values()));

        for (String key : mapStringQuestion.keySet()) {
            when(answerQuestionConverterMock.questionAnswerToStringWithCorrectAnswer(mapStringQuestion.get(key)))
                    .thenReturn(key);
        }

        List<String> questionStringList = questionService.getQuestionStringList();

        assertThat(questionStringList).hasSize(mapStringQuestion.size())
                .isEqualTo(new ArrayList<>(mapStringQuestion.keySet()));
    }

    private static Map<String, Question> getQuestionListWithTestData() {
        Map<String, Question> map = new HashMap<>();

        String questionFirst = "Which of the above is the interface?";
        Answer answerFirstForQuestionFirst = new Answer("List<E>", true);
        Answer answerSecondForQuestionFirst = new Answer("ArrayList<E>", false);
        List<Answer> answerListForQuestionFirst = new ArrayList<>();
        answerListForQuestionFirst.add(answerFirstForQuestionFirst);
        answerListForQuestionFirst.add(answerSecondForQuestionFirst);
        String printStringForQuestionFirst
                = "Question: Which of the above is the interface? Answer options: List<E>(true), ArrayList<E>(false)";

        map.put(printStringForQuestionFirst, new Question(questionFirst, answerListForQuestionFirst));

        String questionSecond
                = "What will the following code output: int a = 5; int b = a + 2; System.out.println(a > b ? a : b);?";
        Answer answerFirstForQuestionSecond = new Answer("5", false);
        Answer answerSecondForQuestionSecond = new Answer("7", true);
        List<Answer> answerListForQuestionSecond = new ArrayList<>();
        answerListForQuestionSecond.add(answerFirstForQuestionSecond);
        answerListForQuestionSecond.add(answerSecondForQuestionSecond);
        String printStringForQuestionSecond
                = "Question: What will the following code output: int a = 5; int b = a + 2; System.out.println(a > b ? a : b);? " +
                "Answer options: 5(false), 7(true)";

        map.put(printStringForQuestionSecond, new Question(questionSecond, answerListForQuestionSecond));

        return map;
    }
}
