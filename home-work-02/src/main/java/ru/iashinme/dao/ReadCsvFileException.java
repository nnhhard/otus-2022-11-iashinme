package ru.iashinme.dao;

public class ReadCsvFileException extends RuntimeException {
    public ReadCsvFileException() {
        super("Exception reading CSV file!");
    }
}
