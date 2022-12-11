package ru.iashinme.dao;

public class ReadCsvFileException extends RuntimeException {
    public ReadCsvFileException(Exception e) {
        super(e);
    }

    public ReadCsvFileException(String message) {
        super(message);
    }
}
