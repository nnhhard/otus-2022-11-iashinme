package ru.iashinme;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.iashinme.service.TestingStudentService;

@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestingStudentService testingStudentService = context.getBean(TestingStudentService.class);

        testingStudentService.testingStudentRun();
    }
}