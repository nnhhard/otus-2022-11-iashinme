package ru.iashinme.service;

import org.springframework.stereotype.Service;
import ru.iashinme.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
    private final InputOutputService inputOutputService;

    public StudentServiceImpl(InputOutputService inputOutputService) {
        this.inputOutputService = inputOutputService;
    }

    @Override
    public Student registerStudent() {
        inputOutputService.printMessage("Enter your surname");
        String surname = inputOutputService.readLine();

        inputOutputService.printMessage("Enter your name");
        String name = inputOutputService.readLine();

        return new Student(surname, name);
    }
}
