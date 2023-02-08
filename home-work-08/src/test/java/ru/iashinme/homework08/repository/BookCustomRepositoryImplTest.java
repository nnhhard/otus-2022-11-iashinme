package ru.iashinme.homework08.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.iashinme.homework08.exception.ValidateException;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Репозиторий книг должен ")
@ComponentScan("ru.iashinme.homework08.repository")
public class BookCustomRepositoryImplTest {

    private final static long EXPECTED_UPDATE_COUNT_BOOK = 2;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("корректно обновлять жанр книг")
    public void shouldCorrectUpdateBookGenre() {
        String oldNameGenre = "Поэма";
        var genre = genreRepository.findByName(oldNameGenre).orElseThrow(
                () -> new ValidateException("Genre not find by name " + oldNameGenre)
        );

        String newGenreName = "Лучшая поэма";
        genre.setName(newGenreName);
        genreRepository.save(genre);

        long countUpdate = bookRepository.updateGenreInBook(genre);
        assertThat(countUpdate).isEqualTo(EXPECTED_UPDATE_COUNT_BOOK);

        var books = bookRepository.findAllByGenre_Id(genre.getId());
        assertThat(books)
                .hasSize((int) EXPECTED_UPDATE_COUNT_BOOK)
                .allMatch(book -> book.getGenre().getName().contains(newGenreName));
    }
}
