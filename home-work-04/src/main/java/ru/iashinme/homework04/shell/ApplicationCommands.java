package ru.iashinme.homework04.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.iashinme.homework04.service.TestingStudentService;

@ShellComponent
public class ApplicationCommands {

    private boolean testingStarted = false;

    private final TestingStudentService testingStudentService;

    public ApplicationCommands(TestingStudentService testingStudentService) {
        this.testingStudentService = testingStudentService;
    }

    @ShellMethod(value = "Testing student run", key = {"r", "run"})
    @ShellMethodAvailability(value = "isTestingStarted")
    public void testingStudentRun() {
        testingStudentService.testingStudentRun();
    }

    @ShellMethod(value = "Testing has started", key = {"s", "started"})
    public String testingHasStarted() {
        testingStarted = true;
        return "You can start testing";
    }

    private Availability isTestingStarted() {
        return !testingStarted ? Availability.unavailable("Testing has not started yet"): Availability.available();
    }
}
