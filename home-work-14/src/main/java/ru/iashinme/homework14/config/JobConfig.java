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
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
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
import ru.iashinme.homework14.service.MigrateService;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    public static final String MIGRATE_BOOK_CATALOGUE_JOB_NAME = "migrateBookCatalogueJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final MigrateService migrateService;
    private final MongoTemplate template;

    @Bean
    public MongoItemReader<Author> mongoAuthorReader() {
        return new MongoItemReaderBuilder<Author>()
                .name("mongoAuthorReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Author.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public MongoItemReader<Genre> mongoGenreReader() {
        return new MongoItemReaderBuilder<Genre>()
                .name("mongoGenreReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Genre.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public MongoItemReader<Book> mongoBookReader() {
        return new MongoItemReaderBuilder<Book>()
                .name("mongoBookReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Book.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<Author, AuthorSQL> authorProcessor() {
        return migrateService::authorConvert;
    }

    @Bean
    public ItemProcessor<Genre, GenreSQL> genreProcessor() {
        return migrateService::genreConvert;
    }

    @Bean
    public ItemProcessor<Book, BookSQL> bookProcessor() {
        return migrateService::bookConvert;
    }

    @Bean
    public JdbcBatchItemWriter<AuthorSQL> jpaAuthorWriter() {
        return new JdbcBatchItemWriterBuilder<AuthorSQL>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into AUTHORS (ID, NAME) values (:id, :name)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<GenreSQL> jpaGenreWriter() {
        return new JdbcBatchItemWriterBuilder<GenreSQL>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into GENRES (ID, NAME) values (:id, :name)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<BookSQL> jpaBookWriter() {
        return new JdbcBatchItemWriterBuilder<BookSQL>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into BOOKS (ID, NAME, AUTHOR_ID, GENRE_ID) values (:id, :name, :author.id, :genre.id)")
                .dataSource(dataSource)
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
    public Step convertAuthors(JdbcBatchItemWriter<AuthorSQL> authorWriter,
                               ItemReader<Author> mongoAuthorReader,
                               ItemProcessor<Author, AuthorSQL> authorProcessor) {
        return stepBuilderFactory.get("convertAuthors")
                .<Author, AuthorSQL>chunk(CHUNK_SIZE)
                .reader(mongoAuthorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }

    @Bean
    public Step convertGenres(JdbcBatchItemWriter<GenreSQL> genreWriter,
                              ItemReader<Genre> mongoGenreReader,
                              ItemProcessor<Genre, GenreSQL> genreProcessor
    ) {
        return stepBuilderFactory.get("convertGenres")
                .<Genre, GenreSQL>chunk(CHUNK_SIZE)
                .reader(mongoGenreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }

    @Bean
    public Step convertBooks(JdbcBatchItemWriter<BookSQL> bookWriter,
                             ItemReader<Book> mongoBookReader,
                             ItemProcessor<Book, BookSQL> bookProcessor
    ) {
        return stepBuilderFactory.get("convertBooks")
                .<Book, BookSQL>chunk(CHUNK_SIZE)
                .reader(mongoBookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }
}