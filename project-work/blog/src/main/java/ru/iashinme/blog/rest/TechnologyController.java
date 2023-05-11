package ru.iashinme.blog.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.blog.dto.TechnologyDto;
import ru.iashinme.blog.service.TechnologyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class TechnologyController {

    private final TechnologyService technologyService;

    @Operation(summary = "Get all technology")
    @GetMapping("/technology")
    public List<TechnologyDto> findAll() {
        return technologyService.findAll();
    }

    @Operation(summary = "Get technology by id")
    @GetMapping("/technology/{id}")
    public TechnologyDto findById(@PathVariable Long id) {
        return technologyService.findById(id);
    }

    @Operation(summary = "Delete technology")
    @DeleteMapping("/technology/{id}")
    public void deleteById(@PathVariable Long id) {
        technologyService.deleteById(id);
    }

    @Operation(summary = "Create technology")
    @PostMapping("/technology")
    public TechnologyDto save(@RequestBody TechnologyDto technologyDto) {
        return technologyService.save(technologyDto);
    }

    @Operation(summary = "Edit technology")
    @PutMapping("/technology")
    public TechnologyDto edit(@RequestBody TechnologyDto technologyDto) {
        return technologyService.edit(technologyDto);
    }
}