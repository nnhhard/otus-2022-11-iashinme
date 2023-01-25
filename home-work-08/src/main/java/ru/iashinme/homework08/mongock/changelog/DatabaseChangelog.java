package ru.iashinme.homework08.mongock.changelog;

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

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "iashinme")
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "iashinme")
    public void insertGenres(GenreRepository genreRepository) {
        genreRepository.save(new Genre("Проза"));
        genreRepository.save(new Genre("Драма"));
        genreRepository.save(new Genre("Стихотворение"));
        genreRepository.save(new Genre("Поэма"));
    }

    @ChangeSet(order = "003", id = "insertAuthors", author = "iashinme")
    public void insertAuthors(AuthorRepository authorRepository) {
        authorRepository.save(new Author("Гоголь", "Николай", "Васильевич"));
        authorRepository.save(new Author("Толстой", "Лев", "Николаевич"));
        authorRepository.save(new Author("Достоевский", "Фёдор", "Михайлович"));
        authorRepository.save(new Author("Пушкин", "Александр", "Сергеевич"));
        authorRepository.save(new Author("Лермонтов", "Михаил", "Юрьевич"));

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
        var book = new Book("Мертвые души", genre);
        book.getAuthors().add(author);
        bookRepository.save(book);
    }

    @ChangeSet(order = "005", id = "insertBookComments", author = "iashinme")
    public void insertBook(BookRepository bookRepository,
                           CommentRepository commentRepository) throws Exception {
        var book = bookRepository.findByName("Мертвые души")
                .stream()
                .findFirst().orElseThrow(
                        () -> new Exception("Migration error - book not find")
                );

        commentRepository.save(new Comment(book.getId(), "Сколько раз перечитываю Мертвые души - столько нахожу нового и незамеченного."));
        commentRepository.save(new Comment(book.getId(), "Автор проявил себя как мастер сатиры - смех сквозь сквозь слезы."));
    }
}
