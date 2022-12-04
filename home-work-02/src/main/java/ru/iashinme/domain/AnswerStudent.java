package ru.iashinme.domain;

import java.util.ArrayList;
import java.util.List;

public class AnswerStudent {
    private Student student;
    private final List<Answer> studentAnswersList;

    public AnswerStudent(Student student) {
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
}
