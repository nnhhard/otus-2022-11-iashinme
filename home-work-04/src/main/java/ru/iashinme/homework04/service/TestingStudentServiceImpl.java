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
    private final LocalizedIOService iOServiceLocalized;

    public TestingStudentServiceImpl(
            QuestionService questionService,
            StudentService studentService,
            QuestionConverter questionConverter,
            TestingParamProvider testingParamProvider,
            LocalizedIOService iOServiceLocalized
    ) {
        this.questionService = questionService;
        this.studentService = studentService;
        this.questionConverter = questionConverter;
        this.testingParamProvider = testingParamProvider;
        this.iOServiceLocalized = iOServiceLocalized;
    }

    @Override
    public void testingStudentRun() {
        iOServiceLocalized.printLocalizeMessage("message.start-testing-student");
        Student student = studentService.registerStudent();

        try {
            var questions = questionService.getQuestionList();
            TestResult testResultStudent = executeTestFor(student, questions);
            printResultTest(testResultStudent);
            printQuestionsWithRightAnswer(questions);
        } catch (QuestionsReadingException e) {
            iOServiceLocalized.printMessage(e.getMessage());
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
        iOServiceLocalized.printLocalizeMessage("message.enter-number-answer",
                questionConverter.questionAnswerToStringWithAnswerIndex(question));

        while (true) {
            try {
                int answerNumber = iOServiceLocalized.readInt();
                testResult.addAnswer(question.getAnswers().get(answerNumber));
                break;
            } catch (Exception e) {
                iOServiceLocalized.printLocalizeMessage("message.error-input");
            }
        }
    }

    private void printResultTest(TestResult testResult) {
        int numberRightAnswer = testResult.getNumberRightAnswerStudent();
        String messageResult =
                numberRightAnswer >= testingParamProvider.getNumberOfCorrectAnswersForTest()
                        ? iOServiceLocalized.getLocalizeMessage("message.successfully-passed-test")
                        : iOServiceLocalized.getLocalizeMessage("message.fail-test");

        iOServiceLocalized.printLocalizeMessage("message.result-test",
                testResult.getStudent().getSurname(),
                testResult.getStudent().getName(),
                messageResult,
                numberRightAnswer
        );
    }
    private void printQuestionsWithRightAnswer(List<Question> questions) {
        iOServiceLocalized.printLocalizeMessage("message.check-question");
        questions
                .stream()
                .map(questionConverter::questionAnswerToStringWithCorrectAnswer)
                .forEach(iOServiceLocalized::printMessage);
    }
}
