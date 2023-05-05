package ru.iashinme.blog.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.iashinme.blog.service.TechnologyService;

@Component
@RequiredArgsConstructor
public class TimeRequestCheckHealthIndicator implements HealthIndicator {

    private final TechnologyService technologyService;

    @Override
    public Health health() {
        long startTime = System.currentTimeMillis();
        technologyService.findAll();
        long endTime = System.currentTimeMillis();

        if(endTime - startTime > 1000) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Долгое выполнение запроса = " + (endTime - startTime) + "ms")
                    .build();
        }
        return Health.up().withDetail("message", "Все ок!").build();
    }
}
