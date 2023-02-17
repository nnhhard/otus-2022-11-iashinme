package ru.iashinme.homework10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.homework10.dto.CommentDto;
import ru.iashinme.homework10.dto.CommentSmallDto;
import ru.iashinme.homework10.dto.CommentWithoutBookDto;
import ru.iashinme.homework10.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/v1/book/{bookId}/comments")
    public List<CommentWithoutBookDto> findByBookId(@PathVariable("bookId") Long bookId) {
        return commentService.findAllByBookId(bookId);
    }

    @PostMapping("/api/v1/comment")
    public CommentDto create(@RequestBody CommentSmallDto commentSmallDto) {
        return commentService.create(commentSmallDto.getBookId(), commentSmallDto.getCommentMessage());
    }

    @DeleteMapping("/api/v1/comment/{id}")
    public void delete(@PathVariable("id") Long id) {
        commentService.deleteById(id);
    }
}
