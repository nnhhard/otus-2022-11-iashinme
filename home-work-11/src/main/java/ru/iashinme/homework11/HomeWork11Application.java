package ru.iashinme.homework11;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableMongock
@SpringBootApplication
@EnableConfigurationProperties
public class HomeWork11Application {

    public static void main(String[] args) {
        SpringApplication.run(HomeWork11Application.class, args);
    }

}
