package ru.iashinme.blog.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.blog.dto.CustomUserDetails;
import ru.iashinme.blog.dto.PostDto;
import ru.iashinme.blog.dto.PostRequestDto;
import ru.iashinme.blog.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Get posts by id")
    @GetMapping(value = "/post/{id}")
    public PostDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @Operation(summary = "Get all posts")
    @GetMapping(value = "/post")
    public List<PostDto> findAll() {
        return postService.findAll();
    }

    @Operation(summary = "Get all post by technology")
    @GetMapping(value = "/post/technology/{technologyId}")
    public List<PostDto> findAllByTechnology(@PathVariable Long technologyId) {
        return postService.findAllByTechnology(technologyId);
    }

    @Operation(summary = "Get all post by author")
    @GetMapping(value = "/post/author/{authorId}")
    public List<PostDto> findAllByAuthor(@PathVariable Long authorId) {
        return postService.findAllByTechnology(authorId);
    }

    @Operation(summary = "Find post by title contains key")
    @GetMapping(value = "/post/title/{search}")
    public List<PostDto> search(@PathVariable String search) {
        return postService.search(search);
    }

    @Operation(summary = "Create post")
    @PostMapping(value = "/post")
    public PostDto save(@RequestBody PostRequestDto postRequestDto) {
        return postService.save(postRequestDto,
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        );
    }

    @Operation(summary = "Edit post")
    @PutMapping(value = "/post")
    public PostDto edit(@RequestBody PostRequestDto postRequestDto) {
        return postService.edit(postRequestDto,
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        );
    }

    @Operation(summary = "Delete post")
    @DeleteMapping("/post/{id}")
    public void delete(@PathVariable("id") Long id) {
        postService.delete(id);
    }
}
