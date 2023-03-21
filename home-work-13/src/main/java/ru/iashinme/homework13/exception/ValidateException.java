package ru.iashinme.homework13.exception;

public class ValidateException extends RuntimeException {

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Exception e) {
        super(message, e);
    }
}
