package ru.iashinme.homework18.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.homework18.dto.GenreDto;
import ru.iashinme.homework18.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/api/v1/genre")
    public List<GenreDto> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/api/v1/genre/{id}")
    public GenreDto findById(@PathVariable long id) {
        return genreService.findById(id);
    }

    @DeleteMapping("/api/v1/genre/{id}")
    public void deleteById(@PathVariable long id) {
        genreService.deleteById(id);
    }

    @PostMapping("/api/v1/genre")
    public GenreDto createGenre(@RequestBody GenreDto genreDto) {
        return genreService.create(genreDto.getName());
    }

    @PutMapping("/api/v1/genre")
    public GenreDto updateGenre(@RequestBody GenreDto genreDto) {
        return genreService.update(genreDto.getId(), genreDto.getName());
    }

}
