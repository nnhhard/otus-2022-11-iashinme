package ru.iashinme.service;

import org.springframework.stereotype.Service;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.AnswerStudent;
import ru.iashinme.domain.Student;

@Service
public class AnswerStudentServiceImpl implements AnswerStudentService {
    private final InputOutputService inputOutputService;

    public AnswerStudentServiceImpl(InputOutputService inputOutputService) {
        this.inputOutputService = inputOutputService;
    }

    @Override
    public AnswerStudent registerStudent() {
        inputOutputService.printMessage("Enter your surname");
        String surname = inputOutputService.readLine();

        inputOutputService.printMessage("Enter your name");
        String name = inputOutputService.readLine();

        return new AnswerStudent(new Student(surname, name));
    }

    @Override
    public int getNumberRightAnswerStudent(AnswerStudent answerStudent) {
        return (int) answerStudent
                .getStudentAnswersList()
                .stream()
                .filter(Answer::isCorrect)
                .count();
    }
}
