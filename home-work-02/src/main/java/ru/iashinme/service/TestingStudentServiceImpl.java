package ru.iashinme.service;

import org.springframework.stereotype.Service;
import ru.iashinme.config.AppSettingTestingParamProvider;
import ru.iashinme.domain.Question;
import ru.iashinme.domain.Student;
import ru.iashinme.domain.TestResult;

@Service
public class TestingStudentServiceImpl implements TestingStudentService {

    private final QuestionService questionService;
    private final InputOutputService inputOutputService;
    private final StudentService studentService;
    private final AnswerQuestionConverter answerQuestionConverter;
    private final AppSettingTestingParamProvider appSettingTestingParamProvider;

    public TestingStudentServiceImpl(
            QuestionService questionService,
            InputOutputService inputOutputService,
            StudentService studentService,
            AnswerQuestionConverterImpl answerQuestionConverter,
            AppSettingTestingParamProvider appSettingTestingParamProvider
    ) {
        this.questionService = questionService;
        this.inputOutputService = inputOutputService;
        this.studentService = studentService;
        this.answerQuestionConverter = answerQuestionConverter;
        this.appSettingTestingParamProvider = appSettingTestingParamProvider;
    }

    @Override
    public void testingStudentRun() {
        inputOutputService.printMessage("Testing student:");

        Student student = studentService.registerStudent();
        TestResult testResultStudent = executeTestFor(student);
        printResultTest(testResultStudent);

        inputOutputService.printMessage("Question with right answers check:");
        questionService.getQuestionStringList().forEach(inputOutputService::printMessage);
    }

    private TestResult executeTestFor(Student student) {
        TestResult testResult = new TestResult(student);
        questionService.getQuestionList().forEach(
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
                numberRightAnswer >= appSettingTestingParamProvider.getNumberOfCorrectAnswersForTest()
                        ? appSettingTestingParamProvider.getMessageSuccessfullyPassedTest()
                        : appSettingTestingParamProvider.getMessageFailTest();

        inputOutputService.printMessage(String.format(appSettingTestingParamProvider.getFormatMessageResultTest(),
                testResult.getStudent().getSurname(),
                testResult.getStudent().getName(),
                messageResult,
                numberRightAnswer
        ));
    }
}
