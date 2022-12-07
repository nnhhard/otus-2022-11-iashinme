package ru.iashinme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppSettings implements AppSettingTestingParamProvider, AppSettingCsvPathProvider {

    private final Integer numberOfCorrectAnswersForTest;
    private final String formatMessageResultTest;
    private final String messageSuccessfullyPassedTest;
    private final String messageFailTest;
    private final String resourceName;

    public AppSettings(
            @Value("${number-of-correct-answers-for-test}") Integer numberOfCorrectAnswersForTest,
            @Value("${format-message-result-test}") String formatMessageResultTest,
            @Value("${message-fail-test}") String messageFailTest,
            @Value("${message-successfully-passed-test}") String messageSuccessfullyPassedTest,
            @Value("${csv-file-path}") String resourceName
    ) {
        this.numberOfCorrectAnswersForTest = numberOfCorrectAnswersForTest;
        this.formatMessageResultTest = formatMessageResultTest;
        this.messageFailTest = messageFailTest;
        this.messageSuccessfullyPassedTest = messageSuccessfullyPassedTest;
        this.resourceName = resourceName;
    }

    @Override
    public Integer getNumberOfCorrectAnswersForTest() {
        return numberOfCorrectAnswersForTest;
    }

    @Override
    public String getFormatMessageResultTest() {
        return formatMessageResultTest;
    }

    @Override
    public String getMessageSuccessfullyPassedTest() {
        return messageSuccessfullyPassedTest;
    }

    @Override
    public String getMessageFailTest() {
        return messageFailTest;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }
}
