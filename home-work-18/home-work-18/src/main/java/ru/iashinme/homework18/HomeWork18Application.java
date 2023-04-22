package ru.iashinme.homework18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class HomeWork18Application {
    public static void main(String[] args) {
        SpringApplication.run(HomeWork18Application.class, args);
    }

}
