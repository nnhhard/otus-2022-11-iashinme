package ru.iashinme.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.iashinme.domain.AnswerStudent;
import ru.iashinme.domain.Question;

@Service
public class TestingStudentServiceImpl implements TestingStudentService {

    private final Integer numberOfCorrectAnswersForTest;
    private final String formatMessageResultTest;
    private final String messageSuccessfullyPassedTest;
    private final String messageFailTest ;
    private final QuestionServiceImpl questionService;
    private final InputOutputService inputOutputService;
    private final AnswerStudentService answerStudentService;
    private final AnswerQuestionConverterImpl answerQuestionConverter;

    public TestingStudentServiceImpl(
            @Value("${number-of-correct-answers-for-test}") Integer numberOfCorrectAnswersForTest,
            @Value("${format-message-result-test}") String formatMessageResultTest,
            @Value("${message-fail-test") String messageFailTest,
            @Value("${message-successfully-passed-test}") String messageSuccessfullyPassedTest,
            QuestionServiceImpl questionService,
            InputOutputService inputOutputService,
            AnswerStudentService answerStudentService,
            AnswerQuestionConverterImpl answerQuestionConverter) {
        this.questionService = questionService;
        this.inputOutputService = inputOutputService;
        this.answerStudentService = answerStudentService;
        this.numberOfCorrectAnswersForTest = numberOfCorrectAnswersForTest;
        this.answerQuestionConverter = answerQuestionConverter;
        this.formatMessageResultTest = formatMessageResultTest;
        this.messageFailTest = messageFailTest;
        this.messageSuccessfullyPassedTest = messageSuccessfullyPassedTest;
    }

    @Override
    public void testingStudentRun() {
        inputOutputService.printMessage("Testing student:");

        AnswerStudent answerStudent = answerStudentService.registerStudent();
        enterAnswersStudent(answerStudent);
        printResultTest(answerStudent);

        inputOutputService.printMessage("Question with right answers check:");
        questionService.printQuestionList();
    }

    private void enterAnswersStudent(AnswerStudent answerStudent) {
        questionService.getQuestionList().forEach(
                question -> printQuestionWithAnswerOptionsForEnterAnswerStudent(answerStudent, question)
        );
    }

    private void printQuestionWithAnswerOptionsForEnterAnswerStudent(AnswerStudent answerStudent, Question question) {
        inputOutputService.printMessage("Enter number right answer:\n"
                + answerQuestionConverter.questionAnswerToStringWithAnswerIndex(question));

        while (true) {
            try {
                int answerNumber = Integer.parseInt(inputOutputService.readLine());
                answerStudent.addAnswer(question.getAnswers().get(answerNumber));
                break;
            } catch (Exception e) {
                inputOutputService.printMessage("Error entering the answer, " +
                        "please enter the number of the correct answer from the suggested ones");
            }
        }
    }

    private void printResultTest(AnswerStudent answerStudent) {
        int numberRightAnswer = answerStudentService.getNumberRightAnswerStudent(answerStudent);
        String messageResult =
                numberRightAnswer >= numberOfCorrectAnswersForTest
                        ? messageSuccessfullyPassedTest
                        : messageFailTest;

        inputOutputService.printMessage(String.format(formatMessageResultTest,
                answerStudent.getStudent().getSurname(),
                answerStudent.getStudent().getName(),
                messageResult,
                numberRightAnswer
        ));
    }
}
