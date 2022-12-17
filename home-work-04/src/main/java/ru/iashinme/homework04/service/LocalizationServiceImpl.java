package ru.iashinme.homework04.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.iashinme.homework04.config.LocaleProvider;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;
    private final LocaleProvider localeProvider;

    public LocalizationServiceImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    @Override
    public String getLocalizeMessage(String message, Object... objects) {
        return messageSource.getMessage(message, objects, localeProvider.getLocale());
    }
}
