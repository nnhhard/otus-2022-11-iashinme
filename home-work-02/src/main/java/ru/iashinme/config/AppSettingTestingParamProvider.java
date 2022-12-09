package ru.iashinme.config;

public interface AppSettingTestingParamProvider {

    int getNumberOfCorrectAnswersForTest();
    String getFormatMessageResultTest();
    String getMessageSuccessfullyPassedTest();
    String getMessageFailTest();
}
