package ru.iashinme.homework08.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.iashinme.homework08.model.Author;
import ru.iashinme.homework08.model.Book;
import ru.iashinme.homework08.model.Comment;
import ru.iashinme.homework08.model.Genre;
import ru.iashinme.homework08.repository.AuthorRepository;
import ru.iashinme.homework08.repository.BookRepository;
import ru.iashinme.homework08.repository.CommentRepository;
import ru.iashinme.homework08.repository.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "iashinme", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "iashinme")
    public void insertGenres(GenreRepository genreRepository) {
        genreRepository.save(new Genre("Поэма"));
    }

    @ChangeSet(order = "003", id = "insertAuthors", author = "iashinme")
    public void insertAuthors(AuthorRepository authorRepository) {
        authorRepository.save(new Author("Гоголь", "Николай", "Васильевич"));
    }

    @ChangeSet(order = "004", id = "insertBook", author = "iashinme")
    public void insertBook(BookRepository bookRepository,
                           GenreRepository genreRepository,
                           AuthorRepository authorRepository) throws Exception {
        var genre
                = genreRepository
                .findByName("Поэма")
                .stream()
                .findFirst()
                .orElseThrow(
                        () -> new Exception("Migration error - genre not find")
                );
        var author
                = authorRepository
                .findBySurnameAndNameAndPatronymic("Гоголь", "Николай", "Васильевич")
                .orElseThrow(() -> new Exception("Migration error - author not find"));
        var book = new Book("Мертвые души 1 Том", genre);
        book.getAuthors().add(author);
        var bookSecond = new Book("Мертвые души 2 Том", genre);
        bookSecond.getAuthors().add(author);

        bookRepository.saveAll(List.of(book,bookSecond));
    }

}
