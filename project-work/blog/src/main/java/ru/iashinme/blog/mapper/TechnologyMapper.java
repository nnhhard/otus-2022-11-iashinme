package ru.iashinme.blog.mapper;

import org.springframework.stereotype.Component;
import ru.iashinme.blog.dto.TechnologyDto;
import ru.iashinme.blog.model.Technology;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TechnologyMapper {
    public List<TechnologyDto> entityToDto(List<Technology> technologies) {
        return technologies.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public TechnologyDto entityToDto(Technology technology) {
        return TechnologyDto
                .builder()
                .id(technology.getId())
                .name(technology.getName())
                .build();
    }
}
