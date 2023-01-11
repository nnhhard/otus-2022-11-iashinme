package ru.iashin.homework06.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.iashin.homework06.model.Book;
import ru.iashin.homework06.model.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryJpaTest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 2;
    private static final long FIRST_BOOK_ID = -1L;
    private static final int EXPECTED_QUERIES_COUNT = 2;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужной книги по ее id")
    @Test
    void shouldFindExpectedBookById() {
        Optional<Book> optionalActualBook = bookRepositoryJpa.findById(FIRST_BOOK_ID);

        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);

        System.out.println(optionalActualBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        var books = bookRepositoryJpa.findAll();
        assertThat(books)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getAuthor().size() > 0);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("считать общее количество книг")
    @Test
    void shouldCalcBooksCount() {
        long booksCount = bookRepositoryJpa.count();
        assertThat(booksCount).isEqualTo(EXPECTED_NUMBER_OF_BOOKS);
    }

    @DisplayName("удалять книгу по ее id")
    @Test
    void shouldDeleteBookById() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        em.detach(book);

        bookRepositoryJpa.deleteById(book.getId());
        var findBook = bookRepositoryJpa.findById(book.getId());

        assertThat(findBook).isNotPresent();
    }

    @DisplayName("изменять книгу")
    @Test
    void shouldUpdateBookName() {
        var book = bookRepositoryJpa.findById(FIRST_BOOK_ID).orElseThrow(NullPointerException::new);
        String oldName = book.getName();
        String newName = "Новое название";
        em.detach(book);

        book.setName(newName);

        book.getComments().add(new Comment(book.getId(), "Ну очень скучная книга"));

        Book actualBook = bookRepositoryJpa.save(book);


        assertThat(actualBook.getName())
                .isNotEqualTo(oldName)
                .isEqualTo(newName);

        System.out.println(actualBook);
    }
}
