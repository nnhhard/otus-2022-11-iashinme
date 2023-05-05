package ru.iashinme.filestore.advice;

import ru.iashinme.filestore.exception.ValidateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<String> handleException(ValidateException validateException) {
        return ResponseEntity.badRequest().body(validateException.getMessage());
    }
}