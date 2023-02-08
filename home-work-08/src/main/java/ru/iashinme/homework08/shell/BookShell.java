package ru.iashinme.homework08.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.iashinme.homework08.service.BookConverter;
import ru.iashinme.homework08.service.BookService;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    private final BookService bookService;
    private final BookConverter bookConverter;

    @ShellMethod(value = "Get book by id", key = {"grt-book-by-id", "getb"})
    public String getBookById(@ShellOption String id) {
        return bookConverter.bookWithAllInfoDtoToString(bookService.getBookById(id));
    }

    @ShellMethod(value = "Get book list", key = {"get-book-list", "getallb"})
    public String getAllBooks() {
        return bookConverter.bookWithIdNameGenreDtoListToString(bookService.getAllBooks());

    }

    @ShellMethod(value = "Create book", key = {"create-book", "cb"})
    public String createBook(@ShellOption String name, @ShellOption String genreId) {
        return bookConverter.bookWithIdNameGenreDtoToString(bookService.createBook(name, genreId));
    }

    @ShellMethod(value = "Delete book", key = {"delete-book", "delb"})
    public String deleteBook(String id) {
        bookService.deleteBookById(id);
        return "Delete book successfully!";
    }

    @ShellMethod(value = "Get books counts", key = {"get-book-counts", "countb"})
    public long countBooks() {
        return bookService.countBooks();
    }

    @ShellMethod(value = "Update book", key = {"update-book", "ub"})
    public String updateBook(@ShellOption String id,
                             @ShellOption String name,
                             @ShellOption String genreId) {
        return bookConverter.bookWithIdNameGenreDtoToString(bookService.updateBook(id, name, genreId));
    }

    @ShellMethod(value = "Add author in book", key = {"add-author-book", "aab"})
    public String addAuthorBook(@ShellOption String id,
                                @ShellOption String authorId) {
        return bookConverter.bookWithAllInfoDtoToString(bookService.addAuthorForBook(id, authorId));
    }

    @ShellMethod(value = "Delete author in book", key = {"del-author-book", "dab"})
    public String deleteAuthorBook(@ShellOption String id,
                                   @ShellOption String authorId) {
        return bookConverter.bookWithAllInfoDtoToString(bookService.deleteAuthorInBook(id, authorId));
    }
}
