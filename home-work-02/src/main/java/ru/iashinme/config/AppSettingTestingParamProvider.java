package ru.iashinme.config;

public interface AppSettingTestingParamProvider {

    public Integer getNumberOfCorrectAnswersForTest();
    public String getFormatMessageResultTest();
    public String getMessageSuccessfullyPassedTest();
    public String getMessageFailTest();
}
