package ru.iashinme.blog.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.blog.dto.CommentDto;
import ru.iashinme.blog.dto.CommentRequestDto;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/comment")
    public CommentDto save(@RequestBody CommentRequestDto commentRequestDto) {
        return commentService.save(commentRequestDto,
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    @PutMapping(value = "/comment")
    public CommentDto edit(@RequestBody CommentRequestDto commentRequestDto) {
        return commentService.save(commentRequestDto,
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    @GetMapping(value = "/comment/post/{postId}")
    public List<CommentDto> findByPostId(@PathVariable Long postId) {
        return commentService.findAllByPostId(postId);
    }

    @GetMapping(value = "/comment/{id}")
    public CommentDto findById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @DeleteMapping(value = "/comment/{id}")
    public void delete(@PathVariable Long id) {
        commentService.delete(id);
    }
}
