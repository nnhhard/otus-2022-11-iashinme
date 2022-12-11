package ru.iashinme.service;

import org.springframework.stereotype.Service;
import ru.iashinme.config.TestingParamProvider;
import ru.iashinme.domain.Question;
import ru.iashinme.domain.Student;
import ru.iashinme.domain.TestResult;

import java.util.List;

@Service
public class TestingStudentServiceImpl implements TestingStudentService {

    private final QuestionService questionService;
    private final InputOutputService inputOutputService;
    private final StudentService studentService;
    private final AnswerQuestionConverter answerQuestionConverter;
    private final TestingParamProvider testingParamProvider;

    public TestingStudentServiceImpl(
            QuestionService questionService,
            InputOutputService inputOutputService,
            StudentService studentService,
            AnswerQuestionConverter answerQuestionConverter,
            TestingParamProvider testingParamProvider
    ) {
        this.questionService = questionService;
        this.inputOutputService = inputOutputService;
        this.studentService = studentService;
        this.answerQuestionConverter = answerQuestionConverter;
        this.testingParamProvider = testingParamProvider;
    }

    @Override
    public void testingStudentRun() {
        inputOutputService.printMessage("Testing student:");
        Student student = studentService.registerStudent();
        var questions = questionService.getQuestionList();
        if(!questions.isEmpty()) {
            TestResult testResultStudent = executeTestFor(student, questions);
            printResultTest(testResultStudent);
            inputOutputService.printMessage("Question with right answers check:");
            questionService.getQuestionStringList().forEach(inputOutputService::printMessage);
        }
    }

    private TestResult executeTestFor(Student student, List<Question> questions) {
        TestResult testResult = new TestResult(student);
        questions.forEach(
                question -> printQuestionWithAnswerOptionsForEnterAnswerStudent(testResult, question)
        );

        return testResult;
    }

    private void printQuestionWithAnswerOptionsForEnterAnswerStudent(TestResult testResult, Question question) {
        inputOutputService.printMessage("Enter number right answer:\n"
                + answerQuestionConverter.questionAnswerToStringWithAnswerIndex(question));

        while (true) {
            try {
                int answerNumber = inputOutputService.readInt();
                testResult.addAnswer(question.getAnswers().get(answerNumber));
                break;
            } catch (Exception e) {
                inputOutputService.printMessage("Error entering the answer, " +
                        "please enter the number of the correct answer from the suggested ones");
            }
        }
    }

    private void printResultTest(TestResult testResult) {
        int numberRightAnswer = testResult.getNumberRightAnswerStudent();
        String messageResult =
                numberRightAnswer >= testingParamProvider.getNumberOfCorrectAnswersForTest()
                        ? testingParamProvider.getMessageSuccessfullyPassedTest()
                        : testingParamProvider.getMessageFailTest();

        inputOutputService.printMessage(String.format(testingParamProvider.getFormatMessageResultTest(),
                testResult.getStudent().getSurname(),
                testResult.getStudent().getName(),
                messageResult,
                numberRightAnswer
        ));
    }
}
