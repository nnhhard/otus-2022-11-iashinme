package ru.iashinme.homework04.service;

public interface LocalizedIOService {
    void printMessage(String message);
    String readLine();
    int readInt();
    String getLocalizeMessage(String message, Object... objects);
    void printLocalizeMessage(String message, Object... objects);
}
