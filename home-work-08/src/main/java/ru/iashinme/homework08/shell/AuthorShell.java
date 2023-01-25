package ru.iashinme.homework08.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.iashinme.homework08.service.AuthorConverter;
import ru.iashinme.homework08.service.AuthorService;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {

    private final AuthorService authorService;
    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Get authors countAuthors", key = {"get-author-count", "counta"})
    public long countAuthors() {
        return authorService.countAuthors();
    }

    @ShellMethod(value = "Get all Authors", key = {"get-author-list", "getalla"})
    public String getAllAuthors() {
        return authorConverter.toString(authorService.getAllAuthors());
    }

    @ShellMethod(value = "Get Author by id", key = {"get-author-by-id", "geta"})
    public String getAuthorById(@ShellOption String id) {
        return authorConverter.toString(authorService.getAuthorById(id));
    }

    @ShellMethod(value = "Create author", key = {"create-author", "ca"})
    public String createAuthor(@ShellOption String surname,
                               @ShellOption String name,
                               @ShellOption String patronymic) {
        return "The author has been added successfully! " +
                authorConverter.toString(authorService.createAuthor(surname, name, patronymic));
    }

    @ShellMethod(value = "Update author", key = {"update-author", "ua"})
    public String updateAuthor(@ShellOption String id,
                               @ShellOption String surname,
                               @ShellOption String name,
                               @ShellOption String patronymic) {
        var author = authorService.updateAuthor(id, surname, name, patronymic);
        return "Record changed successfully" + authorConverter.toString(author);
    }

    @ShellMethod(value = "Delete author by id", key = {"delete-author-by-id", "dela"})
    public String deleteAuthor(@ShellOption String id) {
        authorService.deleteAuthorById(id);
        return "The author has been successfully updated! " +
                authorConverter.toString(authorService.getAllAuthors());
    }
}
