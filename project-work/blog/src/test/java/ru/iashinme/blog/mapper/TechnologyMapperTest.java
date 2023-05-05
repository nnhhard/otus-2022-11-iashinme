package ru.iashinme.blog.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iashinme.blog.dto.TechnologyDto;
import ru.iashinme.blog.model.Technology;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Маппер для преобразования Technology(entity) в TechnologyDto ")
@SpringBootTest(classes = TechnologyMapper.class)
public class TechnologyMapperTest {

    @Autowired
    private TechnologyMapper technologyMapper;

    private final static Technology TECHNOLOGY_ENTITY = Technology.builder()
            .id(-1L)
            .name("cpp")
            .build();

    private static final TechnologyDto TECHNOLOGY_DTO = TechnologyDto
            .builder()
            .id(TECHNOLOGY_ENTITY.getId())
            .name(TECHNOLOGY_ENTITY.getName())
            .build();

    @Test
    @DisplayName("должен возарвщать ожидаемый TechnologyDto")
    public void shouldCorrectReturnTechnologyDto() {

        TechnologyDto actualTechnologyDto = technologyMapper.entityToDto(TECHNOLOGY_ENTITY);

        assertThat(actualTechnologyDto)
                .usingRecursiveComparison()
                .isEqualTo(TECHNOLOGY_DTO);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список TechnologyDto")
    public void shouldCorrectReturnListTechnologyDto() {

        List<TechnologyDto> actualTechnologyDto = technologyMapper.entityToDto(List.of(TECHNOLOGY_ENTITY));

        assertThat(actualTechnologyDto)
                .usingRecursiveComparison()
                .isEqualTo(List.of(TECHNOLOGY_DTO));
    }
}
