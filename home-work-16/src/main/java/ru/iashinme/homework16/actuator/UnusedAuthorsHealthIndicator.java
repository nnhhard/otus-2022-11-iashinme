package ru.iashinme.homework16.actuator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.iashinme.homework16.service.AuthorService;

@Component
public class UnusedAuthorsHealthIndicator implements HealthIndicator {

    private final AuthorService authorService;

    private final long checkCountUnusedAuthor;

    public UnusedAuthorsHealthIndicator(AuthorService authorService,
                                        @Value("${checked.count-unused-authors}") long checkCountUnusedAuthor
    ) {
        this.authorService = authorService;
        this.checkCountUnusedAuthor = checkCountUnusedAuthor;
    }

    @Override
    public Health health() {
        var countUnusedAuthors = authorService.countUnusedAuthors();
        if(countUnusedAuthors >= checkCountUnusedAuthor) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Существуют не использованные авторы, их количество = " + countUnusedAuthors)
                    .build();
        }
        return Health.up().withDetail("message", "Все хорошо!").build();
    }
}
