package ru.iashinme.blog.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.iashinme.blog.service.TechnologyService;

@Component
@RequiredArgsConstructor
public class TechnologyCheckHealthIndicator implements HealthIndicator {

    private final TechnologyService technologyService;

    @Override
    public Health health() {
        if(!technologyService.existsTechnology()) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Кто то удалил все технологии!")
                    .build();
        }
        return Health.up().withDetail("message", "Все хорошо!").build();
    }
}
