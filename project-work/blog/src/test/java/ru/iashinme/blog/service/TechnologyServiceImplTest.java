package ru.iashinme.blog.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iashinme.blog.dto.TechnologyDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.TechnologyMapper;
import ru.iashinme.blog.model.Technology;
import ru.iashinme.blog.repository.TechnologyRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы со справочником технологий должен ")
@SpringBootTest(classes = TechnologyServiceImpl.class)
public class TechnologyServiceImplTest {

    @Autowired
    private TechnologyServiceImpl technologyService;

    @MockBean
    private TechnologyRepository technologyRepository;

    @MockBean
    private TechnologyMapper technologyMapper;


    private final static Technology EXPECTED_TECHNOLOGY = Technology.builder()
            .id(-1L)
            .name("cpp")
            .build();

    private static final TechnologyDto EXPECTED_TECHNOLOGY_DTO = TechnologyDto
            .builder()
            .id(EXPECTED_TECHNOLOGY.getId())
            .name(EXPECTED_TECHNOLOGY.getName())
            .build();

    @Test
    @DisplayName("корректно выбрасывать исключение при проверки наименования технологии")
    public void shouldHaveCorrectExceptionForTechnologyName() {
        TechnologyDto technologyDto = TechnologyDto.builder().build();

        assertThatThrownBy(() -> technologyService.save(technologyDto))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Technology name is null or empty!");
    }

    @DisplayName("возвращать ожидаемый список технологий")
    @Test
    void shouldReturnExpectedTechnologyList() {
        when(technologyRepository.findAll()).thenReturn(List.of(EXPECTED_TECHNOLOGY));
        when(technologyMapper.entityToDto(List.of(EXPECTED_TECHNOLOGY))).thenReturn(List.of(EXPECTED_TECHNOLOGY_DTO));

        var actualList = technologyService.findAll();

        assertThat(actualList)
                .containsExactlyInAnyOrderElementsOf(List.of(EXPECTED_TECHNOLOGY_DTO));
    }

    @DisplayName("возвращать ожидаемую технологию по ее id")
    @Test
    void shouldReturnExpectedTechnologyById() {
        when(technologyRepository.findById(any(Long.class))).thenReturn(Optional.of(EXPECTED_TECHNOLOGY));
        when(technologyMapper.entityToDto(EXPECTED_TECHNOLOGY)).thenReturn(EXPECTED_TECHNOLOGY_DTO);

        var actual = technologyService.findById(EXPECTED_TECHNOLOGY.getId());

        assertThat(actual)
                .isEqualTo(EXPECTED_TECHNOLOGY_DTO);
    }

    @DisplayName("добавлять новую технологию")
    @Test
    void shouldCreateTechnology() {
        TechnologyDto technologyDto = TechnologyDto.builder().name("name").build();

        when(technologyRepository.save(any())).thenReturn(EXPECTED_TECHNOLOGY);
        when(technologyMapper.entityToDto(EXPECTED_TECHNOLOGY)).thenReturn(technologyDto);

        var actual = technologyService.save(technologyDto);

        assertThat(actual)
                .isEqualTo(technologyDto);
    }

    @DisplayName("обновлять технологию")
    @Test
    void shouldUpdateTechnology() {
        when(technologyRepository.save(any())).thenReturn(EXPECTED_TECHNOLOGY);
        when(technologyRepository.findById(EXPECTED_TECHNOLOGY.getId())).thenReturn(Optional.of(EXPECTED_TECHNOLOGY));
        when(technologyMapper.entityToDto(EXPECTED_TECHNOLOGY)).thenReturn(EXPECTED_TECHNOLOGY_DTO);

        var actual = technologyService.edit(EXPECTED_TECHNOLOGY_DTO);

        assertThat(actual)
                .isEqualTo(EXPECTED_TECHNOLOGY_DTO);
    }

    @DisplayName("выбрасывать исключение при обновлении технологии")
    @Test
    void shouldReturnThrowByUpdateTechnology() {
        when(technologyRepository.findById(EXPECTED_TECHNOLOGY.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> technologyService.edit(EXPECTED_TECHNOLOGY_DTO))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("Technology with id = -1 not found!");
    }

    @DisplayName("выбрасывать исключение при попытки сохранить технологию с присвоением id")
    @Test
    void shouldReturnThrowBySaveTechnologyWithId() {

        assertThatThrownBy(() -> technologyService.save(EXPECTED_TECHNOLOGY_DTO))
                .isInstanceOf(ValidateException.class)
                .hasMessageContaining("You cannot set the id when creating!");
    }
}
