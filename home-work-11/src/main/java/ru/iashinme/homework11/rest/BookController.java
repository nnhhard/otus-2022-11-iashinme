package ru.iashinme.homework11.rest;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.dto.BookDto;
import ru.iashinme.homework11.mapper.BookMapper;
import ru.iashinme.homework11.repository.BookRepository;
import ru.iashinme.homework11.repository.CommentRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final BookMapper bookMapper;

    @GetMapping("/api/v1/book")
    public Flux<BookDto> getAllBook() {
        return bookRepository.findAll()
                .map(bookMapper::entityToDto)
                .switchIfEmpty(Flux.error(new NotFoundException("Books not found")));
    }

    @GetMapping("/api/v1/book/{id}")
    public Mono<BookDto> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .map(bookMapper::entityToDto)
                .switchIfEmpty(Mono.error(new NotFoundException("Book not found with id = " + id)));
    }

    @DeleteMapping("/api/v1/book/{id}")
    public Mono<Void> deleteBook(@PathVariable String id) {
        return commentRepository.deleteByBook_Id(id)
                .then(bookRepository.deleteById(id));
    }

    @PostMapping("/api/v1/book")
    public Mono<BookDto> addBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(bookDto.toEntity())
                .map(bookMapper::entityToDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Error save book")));
    }

    @PutMapping("/api/v1/book")
    public Mono<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(bookDto.toEntity())
                .map(bookMapper::entityToDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Error update book")));
    }
}