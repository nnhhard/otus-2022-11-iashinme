package ru.iashinme.homework17.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.homework17.dto.BookDto;
import ru.iashinme.homework17.dto.BookSmallDto;
import ru.iashinme.homework17.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/v1/book")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/book/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/api/v1/book/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
    }

    @PostMapping("/api/v1/book")
    public BookDto addBook(@RequestBody BookSmallDto bookSmallDto) {
        return bookService.create(
                bookSmallDto.getName(),
                bookSmallDto.getAuthorId(),
                bookSmallDto.getGenreId()
        );
    }

    @PutMapping("/api/v1/book")
    public BookDto updateBook(@RequestBody BookSmallDto bookSmallDto) {
        return bookService.update(
                bookSmallDto.getId(),
                bookSmallDto.getName(),
                bookSmallDto.getAuthorId(),
                bookSmallDto.getGenreId()
        );
    }
}
