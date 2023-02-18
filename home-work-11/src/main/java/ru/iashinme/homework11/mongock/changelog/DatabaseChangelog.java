package ru.iashinme.homework11.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

import com.mongodb.client.MongoDatabase;
import ru.iashinme.homework11.model.Author;
import ru.iashinme.homework11.model.Book;
import ru.iashinme.homework11.model.Comment;
import ru.iashinme.homework11.model.Genre;
import ru.iashinme.homework11.repository.AuthorRepository;
import ru.iashinme.homework11.repository.BookRepository;
import ru.iashinme.homework11.repository.CommentRepository;
import ru.iashinme.homework11.repository.GenreRepository;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "iashinme", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "iashinme")
    public void insertGenres(GenreRepository genreRepository) {
        genreRepository.save(new Genre("Проза")).block();
        genreRepository.save(new Genre("Драма")).block();
        genreRepository.save(new Genre("Стихотворение")).block();
        genreRepository.save(new Genre("Поэма")).block();
    }

    @ChangeSet(order = "003", id = "insertAuthors", author = "iashinme")
    public void insertAuthors(AuthorRepository authorRepository) {
        authorRepository.save(new Author("Гоголь", "Николай", "Васильевич")).block();
        authorRepository.save(new Author("Толстой", "Лев", "Николаевич")).block();
        authorRepository.save(new Author("Достоевский", "Фёдор", "Михайлович")).block();
        authorRepository.save(new Author("Пушкин", "Александр", "Сергеевич")).block();
        authorRepository.save(new Author("Лермонтов", "Михаил", "Юрьевич")).block();

    }

    @ChangeSet(order = "004", id = "insertBook", author = "iashinme")
    public void insertBook(BookRepository bookRepository,
                           GenreRepository genreRepository,
                           AuthorRepository authorRepository) throws Exception {
        var genre = genreRepository
                .findByName("Поэма")
                .block();
        var author = authorRepository
                .findBySurnameAndNameAndPatronymic("Гоголь", "Николай", "Васильевич").block();

        var book = new Book("Мертвые души", genre, author);
        bookRepository.save(book).block();
    }

    @ChangeSet(order = "005", id = "insertBookComments", author = "iashinme")
    public void insertComments(BookRepository bookRepository,
                               CommentRepository commentRepository) throws Exception {
        var book = bookRepository.findByName("Мертвые души").block();

        commentRepository.save(
                new Comment(book, "Сколько раз перечитываю Мертвые души - столько нахожу нового и незамеченного.")
        ).block();
        commentRepository.save(
                new Comment(book, "Автор проявил себя как мастер сатиры - смех сквозь сквозь слезы.")
        ).block();
    }
}

