package ru.iashinme.homework04.dao;

public class QuestionsReadingException extends RuntimeException {

    public QuestionsReadingException(Exception e) {
        super(e);
    }
    public QuestionsReadingException(String message, Exception e) {
        super(message, e);
    }
}
