package ru.iashinme.config;

public interface TestingParamProvider {

    int getNumberOfCorrectAnswersForTest();
    String getFormatMessageResultTest();
    String getMessageSuccessfullyPassedTest();
    String getMessageFailTest();
}
