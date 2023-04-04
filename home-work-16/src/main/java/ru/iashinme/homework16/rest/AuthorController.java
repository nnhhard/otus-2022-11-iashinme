package ru.iashinme.homework16.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.homework16.dto.AuthorDto;
import ru.iashinme.homework16.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/v1/author")
    public List<AuthorDto> findAll() {
        return authorService.findAll();
    }

    @GetMapping("/api/v1/author/{id}")
    public AuthorDto findById(@PathVariable("id") long id) {
        return authorService.findById(id);
    }

    @DeleteMapping("/api/v1/author/{id}")
    public void deleteById(@PathVariable("id") long id) {
        authorService.deleteById(id);
    }

    @PostMapping("/api/v1/author")
    public AuthorDto addAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.create(
                authorDto.getSurname(),
                authorDto.getName(),
                authorDto.getPatronymic()
        );
    }

    @PutMapping("/api/v1/author")
    public AuthorDto updateAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.update(
                authorDto.getId(),
                authorDto.getSurname(),
                authorDto.getName(),
                authorDto.getPatronymic()
        );
    }
}
