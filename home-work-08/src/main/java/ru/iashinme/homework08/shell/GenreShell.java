package ru.iashinme.homework08.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.iashinme.homework08.service.GenreConverter;
import ru.iashinme.homework08.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class GenreShell {

    private final GenreService genreService;
    private final GenreConverter genreConverter;

    @ShellMethod(value = "Get genre counts", key = {"get-genre-count", "countg"})
    public long countGenres() {
        return genreService.countGenres();
    }

    @ShellMethod(value = "Get all genre", key = {"get-genre-list", "getallg"})
    public String getAllGenres() {
        return genreConverter.toString(genreService.getAllGenres());
    }

    @ShellMethod(value = "Get genre by id", key = {"get-genre-by-id", "getg"})
    public String getGenreById(@ShellOption String id) {
        return genreConverter.toString(genreService.getGenreById(id));
    }

    @ShellMethod(value = "Create genre", key = {"create-genre", "cg"})
    public String createGenre(@ShellOption String name) {
        return "The genre has been added successfully! " +
                genreConverter.toString(genreService.createGenre(name));
    }

    @ShellMethod(value = "Update genre", key = {"update-genre", "ug"})
    public String updateGenre(@ShellOption String id,
                              @ShellOption String name) {
        return "Record changed successfully! " +
                genreConverter.toString(genreService.updateGenre(id, name));
    }

    @ShellMethod(value = "Delete genre by id", key = {"delete-genre-by-id", "delg"})
    public String deleteGenre(@ShellOption String id) {
        genreService.deleteGenreById(id);
        return "The genre has been successfully deleted! "
                + genreConverter.toString(genreService.getAllGenres());
    }
}
