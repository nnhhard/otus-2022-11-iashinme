package ru.iashinme.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.iashinme.dao.QuestionDao;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;
import ru.iashinme.service.InputOutputService;
import ru.iashinme.service.QuestionServiceImpl;
import java.util.ArrayList;
import java.util.List;

@DisplayName("Class QuestionServiceImpl")
public class QuestionServiceTest {

    @Test
    @DisplayName("Should have correct print string")
    public void shouldHaveCorrectFormatPrintString() {

        var questionDaoMock = Mockito.mock(QuestionDao.class);
        List<Question> questionList = new ArrayList<>();
        List<Answer> answerList = new ArrayList<>();
        answerList.add(new Answer("List<E>", true));
        answerList.add(new Answer("ArrayList<E>", false));
        questionList.add(new Question("Which of the above is the interface?", answerList));
        Mockito.when(questionDaoMock.findAll()).thenReturn(questionList);

        var inputOutputServiceMock = Mockito.mock(InputOutputService.class);

        QuestionServiceImpl questionService = new QuestionServiceImpl(
                questionDaoMock,
                inputOutputServiceMock
        );

        questionService.printQuestionList();

        ArgumentCaptor<String> parametersInThePrintMessage = ArgumentCaptor.forClass(String.class);
        Mockito.verify(inputOutputServiceMock).printMessage(parametersInThePrintMessage.capture());
        Assertions.assertEquals(1, parametersInThePrintMessage.getAllValues().size());
        Assertions.assertEquals(
                "Question: Which of the above is the interface? Answer options: List<E>(true), ArrayList<E>(false)",
                parametersInThePrintMessage.getAllValues().get(0)
        );

    }
}
