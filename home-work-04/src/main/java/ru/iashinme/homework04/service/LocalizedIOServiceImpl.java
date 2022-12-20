package ru.iashinme.homework04.service;

import org.springframework.stereotype.Service;

@Service
public class LocalizedIOServiceImpl implements LocalizedIOService {
    private final LocalizationService localizationService;
    private final InputOutputService inputOutputService;

    public LocalizedIOServiceImpl(LocalizationService localizationService, InputOutputService inputOutputService) {
        this.localizationService = localizationService;
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void printMessage(String message) {
        inputOutputService.printMessage(message);
    }

    @Override
    public String readLine() {
        return inputOutputService.readLine();
    }

    @Override
    public int readInt() {
        return inputOutputService.readInt();
    }

    @Override
    public String getLocalizeMessage(String message, Object... objects) {
        return localizationService.getLocalizeMessage(message, objects);
    }

    @Override
    public void printLocalizeMessage(String message, Object... objects) {
        inputOutputService.printMessage(
                getLocalizeMessage(message, objects)
        );
    }
}
