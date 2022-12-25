package ru.iashinme.homework05.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.iashinme.homework05.domain.Book;
import ru.iashinme.homework05.service.BookService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {

    private final BookService bookService;

    @ShellMethod(value = "Create book", key = {"create-book", "cb"})
    public String createBook(@ShellOption String name, @ShellOption long authorId, @ShellOption long genreId) {
        var id = bookService.createBook(name, authorId, genreId);
        return id > 0 ? "The book has been added successfully!" : "Error when adding a book!";
    }

    @ShellMethod(value = "Get book list", key = {"get-book-list", "getallb"})
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @ShellMethod(value = "Delete book", key = {"delete-book", "delb"})
    public String deleteBook(long id) {
        int countDeleteRow = bookService.deleteBookById(id);
        return countDeleteRow > 0 ? "Delete book successfully!" : "Failed delete book!";
    }

    @ShellMethod(value = "Get books countAuthors", key = {"get-book-countAuthors", "countb"})
    public int countBooks() {
        return bookService.countBooks();
    }

    @ShellMethod(value = "Get book by id", key = {"grt-book-by-id", "getb"})
    public Book getBookById(@ShellOption long id) {
        return bookService.getBookById(id);
    }

    @ShellMethod(value = "Update book", key = {"update-book", "ub"})
    public String updateBook(@ShellOption long id,
                             @ShellOption String name,
                             @ShellOption long authorId,
                             @ShellOption long genreId) {
        var numberUpdateRow = bookService.updateBook(id, name, authorId, genreId);
        return numberUpdateRow > 0 ? "The book has been successfully updated!" : "Error updating the book!";
    }
}
