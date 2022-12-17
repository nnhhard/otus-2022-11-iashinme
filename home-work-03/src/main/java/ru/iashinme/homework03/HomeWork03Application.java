package ru.iashinme.homework03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.iashinme.homework03.service.TestingStudentService;

@SpringBootApplication
public class HomeWork03Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(HomeWork03Application.class, args);
        TestingStudentService testingStudentService = context.getBean(TestingStudentService.class);
        testingStudentService.testingStudentRun();
    }

}
