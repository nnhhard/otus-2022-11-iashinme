package ru.iashinme.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iashinme.blog.model.Technology;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyDto {
    private Long id;
    private String name;

    public Technology toEntity() {
        return new Technology(id, name);
    }
}
