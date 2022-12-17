package ru.iashinme.homework03.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppSetting implements LocaleProvider, TestingParamProvider, ResourceNameProvider {

    private Locale locale;

    private String csvFilePath;

    private int numberOfCorrectAnswersForTest;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getResourceName() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public int getNumberOfCorrectAnswersForTest() {
        return numberOfCorrectAnswersForTest;
    }

    public void setNumberOfCorrectAnswersForTest(int numberOfCorrectAnswersForTest) {
        this.numberOfCorrectAnswersForTest = numberOfCorrectAnswersForTest;
    }
}
