package ru.iashinme.blog.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.blog.dto.PostDto;
import ru.iashinme.blog.dto.PostRequestDto;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class PostController {

    private final PostService postService;

    @GetMapping(value = "/post")
    public List<PostDto> findAll() {
        return postService.findAll();
    }

    @GetMapping(value = "/post/technology/{technologyId}")
    public List<PostDto> findAllByTechnology(@PathVariable Long technologyId) {
        return postService.findAllByTechnology(technologyId);
    }

    @GetMapping(value = "/post/author/{authorId}")
    public List<PostDto> findAllByAuthor(@PathVariable Long authorId) {
        return postService.findAllByTechnology(authorId);
    }

    @GetMapping(value = "/post/title/{search}")
    public List<PostDto> search(@PathVariable String search) {
        return postService.search(search);
    }

    @PostMapping(value = "/post")
    public PostDto save(@RequestBody PostRequestDto postRequestDto) {
        return postService.save(postRequestDto,
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        );
    }

    @PutMapping(value = "/post")
    public PostDto edit(@RequestBody PostRequestDto postRequestDto) {
        return postService.edit(postRequestDto,
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        );
    }

    @DeleteMapping("/post/{id}")
    public void delete(@PathVariable("id") Long id) {
        postService.delete(id);
    }
}
