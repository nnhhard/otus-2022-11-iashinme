package ru.iashinme.service;

public class InputOutputServiceImpl implements InputOutputService {

    @Override
    public void printMessage(String message) {
            System.out.println(message);
    }
}
