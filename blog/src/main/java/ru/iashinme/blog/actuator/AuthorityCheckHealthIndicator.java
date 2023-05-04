package ru.iashinme.blog.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.iashinme.blog.service.UserService;

@Component
@RequiredArgsConstructor
public class AuthorityCheckHealthIndicator implements HealthIndicator {

    private final UserService userService;

    @Override
    public Health health() {
        if(!userService.existAuthority()) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Кто то удалил все технологии!")
                    .build();
        }
        return Health.up().withDetail("message", "Все хорошо!").build();
    }
}
