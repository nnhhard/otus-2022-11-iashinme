package ru.iashinme.homework14.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
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
import ru.iashinme.homework14.model.mongo.Author;
import ru.iashinme.homework14.model.mongo.Book;
import ru.iashinme.homework14.model.mongo.Genre;
import ru.iashinme.homework14.model.h2.AuthorSQL;
import ru.iashinme.homework14.model.h2.BookSQL;
import ru.iashinme.homework14.model.h2.GenreSQL;
import ru.iashinme.homework14.service.AuthorService;
import ru.iashinme.homework14.service.BookService;
import ru.iashinme.homework14.service.GenreService;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    public static final String MIGRATE_BOOK_CATALOGUE_JOB_NAME = "migrateBookCatalogueJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
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
    @StepScope
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
    @StepScope
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
    @StepScope
    public ItemProcessor<Author, AuthorSQL> authorProcessor(AuthorService authorService) {
        return authorService::convert;
    }

    @Bean
    @StepScope
    public ItemProcessor<Genre, GenreSQL> genreProcessor(GenreService genreService) {
        return genreService::convert;
    }

    @Bean
    @StepScope
    public ItemProcessor<Book, BookSQL> bookProcessor(BookService bookService) {
        return bookService::convert;
    }

    @Bean
    @StepScope
    public JpaItemWriter<AuthorSQL> jpaAuthorWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<AuthorSQL>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter<GenreSQL> jpaGenreWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<GenreSQL>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    @StepScope
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
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("Start job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("End job");
                    }
                })
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
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Start read");
                    }

                    public void afterRead(Author o) {
                        log.info("End read");
                    }

                    public void onReadError(Exception e) {
                        log.info("Error read");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(List list) {
                        log.info("Start write");
                    }

                    public void afterWrite(List list) {
                        log.info("End write");
                    }

                    public void onWriteError(Exception e, List list) {
                        log.info("Error write");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Author o) {
                        log.info("Start processing");
                    }

                    public void afterProcess(Author o, AuthorSQL o2) {
                        log.info("End processing");
                    }

                    public void onProcessError(Author o, Exception e) {
                        log.info("Error processing");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        log.info("Start pack");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        log.info("End pack");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        log.info("Error pack");
                    }
                })
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
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Start read");
                    }

                    public void afterRead(Genre o) {
                        log.info("End read");
                    }

                    public void onReadError(Exception e) {
                        log.info("Error read");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(List list) {
                        log.info("Start write");
                    }

                    public void afterWrite(List list) {
                        log.info("End write");
                    }

                    public void onWriteError(Exception e, List list) {
                        log.info("Error write");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Genre o) {
                        log.info("Start processing");
                    }

                    public void afterProcess(Genre o, GenreSQL o2) {
                        log.info("End processing");
                    }

                    public void onProcessError(Genre o, Exception e) {
                        log.info("Error processing");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        log.info("Start pack");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        log.info("End pack");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        log.info("Error pack");
                    }
                })
                .build();
    }

    @Bean
    public Step convertBooks(JpaItemWriter<BookSQL> jpaGenreWriter, ItemReader<Book> mongoGenreReader,
                             ItemProcessor<Book, BookSQL> genreProcessor) {
        return stepBuilderFactory.get("convertBooks")
                .<Book, BookSQL>chunk(CHUNK_SIZE)
                .reader(mongoGenreReader)
                .processor(genreProcessor)
                .writer(jpaGenreWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Start read");
                    }

                    public void afterRead(Book o) {
                        log.info("End read");
                    }

                    public void onReadError(Exception e) {
                        log.info("Error read");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(List list) {
                        log.info("Start write");
                    }

                    public void afterWrite(List list) {
                        log.info("End write");
                    }

                    public void onWriteError(Exception e, List list) {
                        log.info("Error write");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Book o) {
                        log.info("Start processing");
                    }

                    public void afterProcess(Book o, BookSQL o2) {
                        log.info("End processing");
                    }

                    public void onProcessError(Book o, Exception e) {
                        log.info("Error processing");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        log.info("Start pack");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        log.info("End pack");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        log.info("Error pack");
                    }
                })
                .build();
    }
}