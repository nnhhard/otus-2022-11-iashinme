package ru.iashinme.homework05;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.iashinme.homework05.service.GenreService;

import java.sql.SQLException;

@SpringBootApplication
public class HomeWork05Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(HomeWork05Application.class);
        var bean = context.getBean(GenreService.class);
/*        bean.deleteGenre();
        bean.createGenre();
        bean.printGenreList();*/
        //Console.main(args);
    }

}
