package ru.iashinme.homework11.rest;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.dto.AuthorDto;
import ru.iashinme.homework11.mapper.AuthorMapper;
import ru.iashinme.homework11.repository.AuthorRepository;
import ru.iashinme.homework11.repository.BookRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;

    @GetMapping("/api/v1/author")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .map(authorMapper::entityToDto)
                .switchIfEmpty(Flux.error(new NotFoundException("Authors not found")));
    }

    @GetMapping("/api/v1/author/{id}")
    public Mono<AuthorDto> findById(@PathVariable("id") String id) {
        return authorRepository.findById(id)
                .map(authorMapper::entityToDto)
                .switchIfEmpty(Mono.error(new NotFoundException("Author not found with id = " + id)));
    }

    @DeleteMapping("/api/v1/author/{id}")
    public Mono<Void> deleteById(@PathVariable("id") String id) {

        return bookRepository.existsBookByAuthor_Id(id)
                .flatMap(result -> Mono.justOrEmpty(result ? null : false))
                .switchIfEmpty(Mono.error(new RuntimeException("Exists books with this author")))
                .flatMap(r -> authorRepository.deleteById(id));
    }

    @PostMapping("/api/v1/author")
    public Mono<AuthorDto> addAuthor(@RequestBody AuthorDto authorDto) {
        return authorRepository.save(authorDto.toEntity())
                .map(authorMapper::entityToDto);
    }

    @PutMapping("/api/v1/author")
    public Mono<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
        return authorRepository.existsById(authorDto.getId())
                .flatMap(result -> Mono.justOrEmpty(!result ? null : true))
                .switchIfEmpty(Mono.error(new RuntimeException("Author not found with id = " + authorDto.getId())))
                .then(authorRepository.save(authorDto.toEntity()))
                .then(bookRepository.updateAuthorInBook(authorDto.toEntity()))
                .flatMap(__ -> Mono.just(authorDto));
    }
}
