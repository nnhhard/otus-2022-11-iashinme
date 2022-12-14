package ru.iashinme.homework03.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.iashinme.homework03.config.LocaleProvider;
import java.util.Locale;

@Component
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;
    private final Locale locale;

    public LocalizationServiceImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.locale = localeProvider.getLocale();
    }

    @Override
    public String getLocalizeMessage(String message, Object... objects) {
        return messageSource.getMessage(message, objects, locale);
    }
}
