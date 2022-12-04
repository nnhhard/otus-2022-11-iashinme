package ru.iashinme.service;

import ru.iashinme.domain.AnswerStudent;

public interface AnswerStudentService {
    AnswerStudent registerStudent();
    int getNumberRightAnswerStudent(AnswerStudent answerStudent);
}
