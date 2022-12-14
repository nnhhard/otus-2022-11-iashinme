package ru.iashinme.dao;

public class QuestionsReadingException extends RuntimeException {
    public QuestionsReadingException(Exception e) {
        super(e);
    }

    public QuestionsReadingException(String message, Exception e) {
        super(message, e);
    }
}
