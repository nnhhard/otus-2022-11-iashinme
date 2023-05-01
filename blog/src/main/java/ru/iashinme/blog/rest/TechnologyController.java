package ru.iashinme.blog.rest;

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

    @GetMapping("/technology/count")
    public Long getCountTechnology() {
        return technologyService.count();
    }

    @GetMapping("/technology")
    public List<TechnologyDto> findAll() {
        return technologyService.findAll();
    }

    @GetMapping("/technology/{id}")
    public TechnologyDto findById(@PathVariable Long id) {
        return technologyService.findById(id);
    }

    @DeleteMapping("/technology/{id}")
    public void deleteById(@PathVariable Long id) {
        technologyService.deleteById(id);
    }

    @PostMapping("/technology")
    public TechnologyDto save(@RequestBody TechnologyDto technologyDto) {
        return technologyService.save(technologyDto);
    }

    @PutMapping("/technology")
    public TechnologyDto edit(@RequestBody TechnologyDto technologyDto) {
        return technologyService.edit(technologyDto);
    }
}
