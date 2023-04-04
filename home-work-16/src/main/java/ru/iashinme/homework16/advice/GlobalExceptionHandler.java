package ru.iashinme.homework16.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.iashinme.homework16.exception.ValidateException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<String> handleException(ValidateException validateException) {
        return ResponseEntity.badRequest().body(validateException.getMessage());
    }
}