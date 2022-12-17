package ru.iashinme.homework04.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework04.config.TestingParamProvider;
import ru.iashinme.homework04.dao.QuestionsReadingException;
import ru.iashinme.homework04.domain.Question;
import ru.iashinme.homework04.domain.Student;
import ru.iashinme.homework04.domain.TestResult;

import java.util.List;

@Service
public class TestingStudentServiceImpl implements TestingStudentService {

    private final QuestionService questionService;
    private final StudentService studentService;
    private final QuestionConverter questionConverter;
    private final TestingParamProvider testingParamProvider;
    private final IOFacadeService iOFacadeService;

    public TestingStudentServiceImpl(
            QuestionService questionService,
            StudentService studentService,
            QuestionConverter questionConverter,
            TestingParamProvider testingParamProvider,
            IOFacadeService iOFacadeService
    ) {
        this.questionService = questionService;
        this.studentService = studentService;
        this.questionConverter = questionConverter;
        this.testingParamProvider = testingParamProvider;
        this.iOFacadeService = iOFacadeService;
    }

    @Override
    public void testingStudentRun() {
        iOFacadeService.printLocalizeMessage("message.start-testing-student");
        Student student = studentService.registerStudent();

        try {
            var questions = questionService.getQuestionList();
            TestResult testResultStudent = executeTestFor(student, questions);
            printResultTest(testResultStudent);
            printQuestionsWithRightAnswer(questions);
        } catch (QuestionsReadingException e) {
            iOFacadeService.printMessage(e.getMessage());
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
        iOFacadeService.printLocalizeMessage("message.enter-number-answer",
                questionConverter.questionAnswerToStringWithAnswerIndex(question));

        while (true) {
            try {
                int answerNumber = iOFacadeService.readInt();
                testResult.addAnswer(question.getAnswers().get(answerNumber));
                break;
            } catch (Exception e) {
                iOFacadeService.printLocalizeMessage("message.error-input");
            }
        }
    }

    private void printResultTest(TestResult testResult) {
        int numberRightAnswer = testResult.getNumberRightAnswerStudent();
        String messageResult =
                numberRightAnswer >= testingParamProvider.getNumberOfCorrectAnswersForTest()
                        ? iOFacadeService.getLocalizeMessage("message.successfully-passed-test")
                        : iOFacadeService.getLocalizeMessage("message.fail-test");

        iOFacadeService.printLocalizeMessage("message.result-test",
                testResult.getStudent().getSurname(),
                testResult.getStudent().getName(),
                messageResult,
                numberRightAnswer
        );
    }
    private void printQuestionsWithRightAnswer(List<Question> questions) {
        iOFacadeService.printLocalizeMessage("message.check-question");
        questions
                .stream()
                .map(questionConverter::questionAnswerToStringWithCorrectAnswer)
                .forEach(iOFacadeService::printMessage);
    }
}
