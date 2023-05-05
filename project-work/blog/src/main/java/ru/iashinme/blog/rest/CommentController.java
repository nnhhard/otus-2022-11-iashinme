package ru.iashinme.blog.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.blog.dto.CommentDto;
import ru.iashinme.blog.dto.CommentRequestDto;
import ru.iashinme.blog.dto.CustomUserDetails;
import ru.iashinme.blog.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Create comment for post")
    @PostMapping(value = "/comment")
    public CommentDto save(@RequestBody CommentRequestDto commentRequestDto) {
        return commentService.save(commentRequestDto,
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    @Operation(summary = "Edit comment for post")
    @PutMapping(value = "/comment")
    public CommentDto edit(@RequestBody CommentRequestDto commentRequestDto) {
        return commentService.save(commentRequestDto,
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    @Operation(summary = "Get all comment for post")
    @GetMapping(value = "/comment/post/{postId}")
    public List<CommentDto> findByPostId(@PathVariable Long postId) {
        return commentService.findAllByPostId(postId);
    }

    @Operation(summary = "Get comment by id")
    @GetMapping(value = "/comment/{id}")
    public CommentDto findById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @Operation(summary = "Delete comment")
    @DeleteMapping(value = "/comment/{id}")
    public void delete(@PathVariable Long id) {
        commentService.delete(id);
    }
}
