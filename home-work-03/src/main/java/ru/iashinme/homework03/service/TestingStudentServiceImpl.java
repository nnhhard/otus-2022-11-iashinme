package ru.iashinme.homework03.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework03.config.TestingParamProvider;
import ru.iashinme.homework03.dao.QuestionsReadingException;
import ru.iashinme.homework03.domain.Question;
import ru.iashinme.homework03.domain.Student;
import ru.iashinme.homework03.domain.TestResult;

import java.util.List;

@Service
public class TestingStudentServiceImpl implements TestingStudentService {

    private final QuestionService questionService;
    private final InputOutputService inputOutputService;
    private final StudentService studentService;
    private final QuestionConverter questionConverter;
    private final TestingParamProvider testingParamProvider;
    private final LocalizationService localizationService;

    public TestingStudentServiceImpl(
            QuestionService questionService,
            InputOutputService inputOutputService,
            StudentService studentService,
            QuestionConverter questionConverter,
            TestingParamProvider testingParamProvider,
            LocalizationService localizationService
    ) {
        this.questionService = questionService;
        this.inputOutputService = inputOutputService;
        this.studentService = studentService;
        this.questionConverter = questionConverter;
        this.testingParamProvider = testingParamProvider;
        this.localizationService = localizationService;
    }

    @Override
    public void testingStudentRun() {
        inputOutputService.printMessage(localizationService.getLocalizeMessage("message.start-testing-student"));
        Student student = studentService.registerStudent();

        try {
            var questions = questionService.getQuestionList();
            TestResult testResultStudent = executeTestFor(student, questions);
            printResultTest(testResultStudent);
            printQuestionsWithRightAnswer(questions);
        } catch (QuestionsReadingException e) {
            inputOutputService.printMessage(e.getMessage());
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
        inputOutputService.printMessage(localizationService.getLocalizeMessage("message.enter-number-answer",
                questionConverter.questionAnswerToStringWithAnswerIndex(question)));

        while (true) {
            try {
                int answerNumber = inputOutputService.readInt();
                testResult.addAnswer(question.getAnswers().get(answerNumber));
                break;
            } catch (Exception e) {
                inputOutputService.printMessage(localizationService.getLocalizeMessage("message.error-input"));
            }
        }
    }

    private void printResultTest(TestResult testResult) {
        int numberRightAnswer = testResult.getNumberRightAnswerStudent();
        String messageResult =
                numberRightAnswer >= testingParamProvider.getNumberOfCorrectAnswersForTest()
                        ? localizationService.getLocalizeMessage("message.successfully-passed-test")
                        : localizationService.getLocalizeMessage("message.fail-test");

        inputOutputService.printMessage(localizationService.getLocalizeMessage("message.result-test",
                testResult.getStudent().getSurname(),
                testResult.getStudent().getName(),
                messageResult,
                numberRightAnswer
        ));
    }
    private void printQuestionsWithRightAnswer(List<Question> questions) {
        inputOutputService.printMessage(localizationService.getLocalizeMessage("message.check-question"));
        questions
                .stream()
                .map(questionConverter::questionAnswerToStringWithCorrectAnswer)
                .forEach(inputOutputService::printMessage);
    }
}
