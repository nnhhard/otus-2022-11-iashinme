package ru.iashinme.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.iashinme.homework14.model.h2.AuthorSQL;
import ru.iashinme.homework14.model.h2.BookSQL;
import ru.iashinme.homework14.model.h2.GenreSQL;
import ru.iashinme.homework14.model.mongo.Author;
import ru.iashinme.homework14.model.mongo.Book;
import ru.iashinme.homework14.model.mongo.Genre;
import ru.iashinme.homework14.service.AuthorService;
import ru.iashinme.homework14.service.BookService;
import ru.iashinme.homework14.service.GenreService;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    public static final String MIGRATE_BOOK_CATALOGUE_JOB_NAME = "migrateBookCatalogueJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public MongoItemReader<Author> mongoAuthorReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Author>()
                .name("mongoAuthorReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Author.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public MongoItemReader<Genre> mongoGenreReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Genre>()
                .name("mongoGenreReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Genre.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public MongoItemReader<Book> mongoBookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Book>()
                .name("mongoBookReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Book.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Author, AuthorSQL> authorProcessor(AuthorService authorService) {
        return authorService::convert;
    }

    @Bean
    public ItemProcessor<Genre, GenreSQL> genreProcessor(GenreService genreService) {
        return genreService::convert;
    }

    @Bean
    public ItemProcessor<Book, BookSQL> bookProcessor(BookService bookService) {
        return bookService::convert;
    }

    @Bean
    public JpaItemWriter<AuthorSQL> jpaAuthorWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<AuthorSQL>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public JpaItemWriter<GenreSQL> jpaGenreWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<GenreSQL>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public JpaItemWriter<BookSQL> jpaBookWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<BookSQL>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Job migrateBookCatalogueJob(@Qualifier("splitFlow") Flow splitFlow,
                                       @Qualifier("convertBooks") Step booksConverter) {
        return jobBuilderFactory.get(MIGRATE_BOOK_CATALOGUE_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(splitFlow)
                .next(booksConverter)
                .end()
                .build();
    }

    @Bean
    public Flow splitFlow(@Qualifier("taskExecutor") TaskExecutor taskExecutor,
                          @Qualifier("convertAuthorsFlow") Flow convertAuthorsFlow,
                          @Qualifier("convertGenresFlow") Flow convertGenresFlow) {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(taskExecutor)
                .add(convertAuthorsFlow, convertGenresFlow)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

    @Bean
    public Flow convertAuthorsFlow(@Qualifier("convertAuthors") Step authorsConverter) {
        return new FlowBuilder<SimpleFlow>("convertAuthorsFlow")
                .start(authorsConverter)
                .build();
    }

    @Bean
    public Flow convertGenresFlow(@Qualifier("convertGenres") Step genresConverter) {
        return new FlowBuilder<SimpleFlow>("convertGenresFlow")
                .start(genresConverter)
                .build();
    }

    @Bean
    public Step convertAuthors(JpaItemWriter<AuthorSQL> jpaAuthorWriter, ItemReader<Author> mongoAuthorReader,
                               ItemProcessor<Author, AuthorSQL> authorProcessor) {
        return stepBuilderFactory.get("convertAuthors")
                .<Author, AuthorSQL>chunk(CHUNK_SIZE)
                .reader(mongoAuthorReader)
                .processor(authorProcessor)
                .writer(jpaAuthorWriter)
                .build();
    }

    @Bean
    public Step convertGenres(JpaItemWriter<GenreSQL> jpaGenreWriter, ItemReader<Genre> mongoGenreReader,
                              ItemProcessor<Genre, GenreSQL> genreProcessor) {
        return stepBuilderFactory.get("convertGenres")
                .<Genre, GenreSQL>chunk(CHUNK_SIZE)
                .reader(mongoGenreReader)
                .processor(genreProcessor)
                .writer(jpaGenreWriter)
                .build();
    }

    @Bean
    public Step convertBooks(JpaItemWriter<BookSQL> jpaGenreWriter, ItemReader<Book> mongoBookReader,
                             ItemProcessor<Book, BookSQL> bookProcessor) {
        return stepBuilderFactory.get("convertBooks")
                .<Book, BookSQL>chunk(CHUNK_SIZE)
                .reader(mongoBookReader)
                .processor(bookProcessor)
                .writer(jpaGenreWriter)
                .build();
    }
}