package ru.iashinme.domain;

import java.util.ArrayList;
import java.util.List;

public class TestResult {
    private Student student;
    private final List<Answer> studentAnswersList;

    public TestResult(Student student) {
        this.student = student;
        studentAnswersList = new ArrayList<>();
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void addAnswer(Answer answer) {
        studentAnswersList.add(answer);
    }

    public void clearAnswers() {
        studentAnswersList.clear();
    }

    public List<Answer> getStudentAnswersList() {
        return studentAnswersList;
    }

    public int getNumberRightAnswerStudent() {
        return (int) studentAnswersList
                .stream()
                .filter(Answer::isCorrect)
                .count();
    }
}
