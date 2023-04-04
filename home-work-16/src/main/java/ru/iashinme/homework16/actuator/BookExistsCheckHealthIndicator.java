package ru.iashinme.homework16.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.iashinme.homework16.service.BookService;

@Component
@RequiredArgsConstructor
public class BookExistsCheckHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    @Override
    public Health health() {
        if(!bookService.existsBook()) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Кто то удалил все книги!")
                    .build();
        }
        return Health.up().withDetail("message", "Все хорошо!").build();
    }
}
