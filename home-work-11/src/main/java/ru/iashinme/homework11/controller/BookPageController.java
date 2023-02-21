package ru.iashinme.homework11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.dto.BookDto;
import ru.iashinme.homework11.mapper.AuthorMapper;
import ru.iashinme.homework11.mapper.BookMapper;
import ru.iashinme.homework11.mapper.GenreMapper;
import ru.iashinme.homework11.repository.AuthorRepository;
import ru.iashinme.homework11.repository.BookRepository;
import ru.iashinme.homework11.repository.GenreRepository;

@Controller
@RequiredArgsConstructor
public class BookPageController {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @GetMapping({"/", "/books"})
    public String findAll() {
        return "books";
    }

    @GetMapping("/books/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        model.addAttribute("book", bookRepository.findById(id)
                .map(bookMapper::entityToDto)
                .switchIfEmpty(Mono.just(BookDto.builder().id("-1").build())));

        model.addAttribute("authors", authorRepository.findAll().map(authorMapper::entityToDto)
                .switchIfEmpty(Flux.empty()));

        model.addAttribute("genres", genreRepository.findAll().map(genreMapper::entityToDto)
                .switchIfEmpty(Flux.empty()));

        return "book";
    }
}
