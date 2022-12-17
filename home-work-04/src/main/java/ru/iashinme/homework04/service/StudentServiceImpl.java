package ru.iashinme.homework04.service;

import org.springframework.stereotype.Service;
import ru.iashinme.homework04.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
    private final InputOutputService inputOutputService;
    private final LocalizationService localizationService;

    public StudentServiceImpl(InputOutputService inputOutputService, LocalizationService localizationService) {
        this.inputOutputService = inputOutputService;
        this.localizationService = localizationService;
    }

    @Override
    public Student registerStudent() {
        inputOutputService.printMessage(localizationService.getLocalizeMessage("message.request-surname"));
        String surname = inputOutputService.readLine();

        inputOutputService.printMessage(localizationService.getLocalizeMessage("message.request-name"));
        String name = inputOutputService.readLine();

        return new Student(surname, name);
    }
}
