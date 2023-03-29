package ru.iashinme.homework14;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class HomeWork14Application {

    public static void main(String[] args) {
        SpringApplication.run(HomeWork14Application.class, args);
    }
}
