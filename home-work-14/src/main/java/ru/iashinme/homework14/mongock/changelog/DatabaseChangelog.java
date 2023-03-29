package ru.iashinme.homework14.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.iashinme.homework14.model.mongo.Author;
import ru.iashinme.homework14.model.mongo.Book;
import ru.iashinme.homework14.model.mongo.Genre;
import ru.iashinme.homework14.repository.mongo.AuthorMongoRepository;
import ru.iashinme.homework14.repository.mongo.BookMongoRepository;
import ru.iashinme.homework14.repository.mongo.GenreMongoRepository;

import javax.validation.ValidationException;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "iashinme", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

   @ChangeSet(order = "002", id = "insertGenres", author = "iashinme")
    public void insertGenres(GenreMongoRepository genreRepository) {
        genreRepository.save(new Genre("Проза"));
        genreRepository.save(new Genre("Драма"));
        genreRepository.save(new Genre("Стихотворение"));
        genreRepository.save(new Genre("Поэма"));
    }

    @ChangeSet(order = "003", id = "insertAuthors", author = "iashinme")
    public void insertAuthors(AuthorMongoRepository authorRepository) {
        authorRepository.save(new Author("Гоголь Николай Васильевич"));
        authorRepository.save(new Author("Толстой Лев Николаевич"));
        authorRepository.save(new Author("Достоевский Фёдор Михайлович"));
        authorRepository.save(new Author("Пушкин Александр Сергеевич"));
        authorRepository.save(new Author("Лермонтов Михаил Юрьевич"));

    }

    @ChangeSet(order = "004", id = "insertBook", author = "iashinme")
    public void insertBook(BookMongoRepository bookRepository,
                           GenreMongoRepository genreRepository,
                           AuthorMongoRepository authorRepository) {
        var genre = genreRepository
                .findByName("Поэма")
                .orElseThrow(
                        () -> new ValidationException("Error migrate book")
                );
        var author = authorRepository
                .findByName("Гоголь Николай Васильевич")
                .orElseThrow(
                        () -> new ValidationException("Error migrate book")
                );;

        var book = new Book("Мертвые души", genre, author);
        bookRepository.save(book);
    }
}
