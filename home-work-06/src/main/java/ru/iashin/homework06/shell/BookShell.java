package ru.iashin.homework06.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.iashin.homework06.service.AuthorService;
import ru.iashin.homework06.service.BookConverter;
import ru.iashin.homework06.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookConverter bookConverter;

    @ShellMethod(value = "Get book by id", key = {"grt-book-by-id", "getb"})
    public String getBookById(@ShellOption long id) {
        return bookConverter.toString(bookService.getBookById(id));
    }

    @ShellMethod(value = "Get book list", key = {"get-book-list", "getallb"})
    public String getAllBooks() {
        return bookConverter.toString(bookService.getAllBooks());

    }

    @ShellMethod(value = "Create book", key = {"create-book", "cb"})
    public String createBook(@ShellOption String name, @ShellOption long genreId) {
        return bookConverter.toString(bookService.createBook(name, genreId));
    }

    @ShellMethod(value = "Delete book", key = {"delete-book", "delb"})
    public String deleteBook(long id) {
        bookService.deleteBookById(id);
        return "Delete book successfully!";
    }

    @ShellMethod(value = "Get books counts", key = {"get-book-counts", "countb"})
    public long countBooks() {
        return bookService.countBooks();
    }

    @ShellMethod(value = "Update book", key = {"update-book", "ub"})
    public String updateBook(@ShellOption long id,
                             @ShellOption String name,
                             @ShellOption long genreId) {
        return bookConverter.toString(bookService.updateBook(id, name, genreId));
    }

    @ShellMethod(value = "Add author in book", key = {"add-author-book", "aab"})
    public String addAuthorBook(@ShellOption long id,
                             @ShellOption long authorId) {
        return bookConverter.toString(bookService.addAuthorForBook(id, authorId));
    }

    @ShellMethod(value = "Delete author in book", key = {"del-author-book", "dab"})
    public String deleteAuthorBook(@ShellOption long id,
                             @ShellOption long authorId) {
        return bookConverter.toString(bookService.deleteAuthorInBook(id, authorId));
    }
}
