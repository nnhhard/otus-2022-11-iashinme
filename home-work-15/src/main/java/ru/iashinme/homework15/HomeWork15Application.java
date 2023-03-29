package ru.iashinme.homework15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.iashinme.homework15.service.CaterpillarServiceImpl;

@SpringBootApplication
public class HomeWork15Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(HomeWork15Application.class, args);
        CaterpillarServiceImpl orderService = ctx.getBean(CaterpillarServiceImpl.class);
        orderService.startGenerateCaterpillarLoop();
    }
}