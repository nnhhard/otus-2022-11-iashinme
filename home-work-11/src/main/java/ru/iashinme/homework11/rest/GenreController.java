package ru.iashinme.homework11.rest;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.iashinme.homework11.dto.GenreDto;
import ru.iashinme.homework11.mapper.GenreMapper;
import ru.iashinme.homework11.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @GetMapping("/api/v1/genre")
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .map(genreMapper::entityToDto)
                .switchIfEmpty(Flux.error(new NotFoundException("Genres not found")));
    }
}
