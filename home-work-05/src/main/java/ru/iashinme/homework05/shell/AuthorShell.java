package ru.iashinme.homework05.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.iashinme.homework05.domain.Author;
import ru.iashinme.homework05.service.AuthorService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {

    private final AuthorService authorService;

    @ShellMethod(value = "Get authors countAuthors", key = {"get-author-countAuthors", "counta"})
    public int countAuthors() {
        return authorService.countAuthors();
    }

    @ShellMethod(value = "Get all Authors", key = {"get-author-list", "getalla"})
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @ShellMethod(value = "Get Author by id", key = {"get-author-by-id", "geta"})
    public Author getAuthorById(@ShellOption Long id) {
        return authorService.getAuthorById(id);
    }

    @ShellMethod(value = "Create author", key = {"create-author", "ca"})
    public String createAuthor(@ShellOption String surname,
                               @ShellOption String name,
                               @ShellOption String patronymic
    ) {
        var id = authorService.createAuthor(surname, name, patronymic);
        return id > 0 ? "The author has been added successfully!" : "Error when adding a author!";
    }

    @ShellMethod(value = "Update author", key = {"update-author", "ua"})
    public String updateAuthor(@ShellOption Long id,
                               @ShellOption String surname,
                               @ShellOption String name,
                               @ShellOption String patronymic) {
        var numberRowUpdate = authorService.updateAuthor(id, surname, name, patronymic);
        return numberRowUpdate > 0 ? "Record changed successfully" : "Failed to change record";
    }

    @ShellMethod(value = "Delete author by id", key = {"delete-author-by-id", "dela"})
    public String deleteAuthor(@ShellOption Long id) {
        int numberDeleteRow = authorService.deleteAuthorById(id);
        return numberDeleteRow > 0 ? "The author has been successfully updated!" : "Error updating the author!";
    }
}
