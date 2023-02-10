package ru.iashinme.homework10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.iashinme.homework10.dto.AuthorDto;
import ru.iashinme.homework10.dto.BookDto;
import ru.iashinme.homework10.dto.GenreDto;
import ru.iashinme.homework10.service.AuthorService;
import ru.iashinme.homework10.service.BookService;
import ru.iashinme.homework10.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookPageController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping({"/", "/books"})
    public String findAll(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        BookDto book = id > 0 ? bookService.findById(id) : BookDto.builder().build();
        model.addAttribute("book", book);

        List<AuthorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);

        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        return "book";
    }
}
