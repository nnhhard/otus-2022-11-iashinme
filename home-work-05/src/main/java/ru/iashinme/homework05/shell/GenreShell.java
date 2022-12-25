package ru.iashinme.homework05.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.iashinme.homework05.domain.Genre;
import ru.iashinme.homework05.service.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class GenreShell {
    private final GenreService genreService;

    @ShellMethod(value = "Get genre countAuthors", key = {"get-genre-countAuthors", "countg"})
    public int countGenres() {
        return genreService.countGenres();
    }

    @ShellMethod(value = "Get all genre", key = {"get-genre-list", "getallg"})
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @ShellMethod(value = "Get genre by id", key = {"get-genre-by-id", "getg"})
    public Genre getGenreById(@ShellOption Long id) {
        return genreService.getGenreById(id);
    }

    @ShellMethod(value = "Create genre", key = {"create-genre", "cg"})
    public String createGenre(@ShellOption String name) {
        var id = genreService.createGenre(name);
        return id > 0 ? "The genre has been added successfully!" : "Error when adding a genre!";
    }

    @ShellMethod(value = "Update genre", key = {"updateAuthor-genre", "ug"})
    public String updateGenre(@ShellOption Long id,
                               @ShellOption String name) {
        var numberRowUpdate = genreService.updateGenre(id, name);
        return numberRowUpdate > 0 ? "Record changed successfully" : "Failed to change record";
    }

    @ShellMethod(value = "Delete genre by id", key = {"delete-genre-by-id", "delg"})
    public String deleteGenre(@ShellOption Long id) {
        int numberDeleteRow = genreService.deleteGenreById(id);
        return numberDeleteRow > 0 ? "The author has been successfully updated!" : "Error updating the author!";
    }
}
