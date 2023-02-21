package ru.iashinme.homework11.rest;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.iashinme.homework11.dto.CommentDto;
import ru.iashinme.homework11.mapper.CommentMapper;
import ru.iashinme.homework11.repository.CommentRepository;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @GetMapping("/api/v1/book/{bookId}/comments")
    public Flux<CommentDto> getAllCommentsForBook(@PathVariable String bookId) {
        return commentRepository.findByBookId(bookId)
                .map(commentMapper::entityToDto)
                .switchIfEmpty(Flux.error(new NotFoundException("Comments not found")));
    }

    @DeleteMapping("/api/v1/book/{bookId}/comments")
    public Mono<Void> deleteAllCommentsForBook(@PathVariable String bookId) {
        return commentRepository.deleteByBook_Id(bookId);
    }
}
