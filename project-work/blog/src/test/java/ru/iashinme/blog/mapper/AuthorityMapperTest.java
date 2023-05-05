package ru.iashinme.blog.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iashinme.blog.dto.AuthorityDto;
import ru.iashinme.blog.model.Authority;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Маппер для преобразования Technology(Authority) в AuthorityDto ")
@SpringBootTest(classes = AuthorityMapper.class)
public class AuthorityMapperTest {

    @Autowired
    private AuthorityMapper authorityMapper;

    private final static Authority AUTHORITY_ENTITY = new Authority(-1L, "ROLE_USER");

    private static final AuthorityDto AUTHORITY_DTO = new AuthorityDto(AUTHORITY_ENTITY.getId(), AUTHORITY_ENTITY.getAuthority());

    @Test
    @DisplayName("должен возарвщать ожидаемый AuthorityDto")
    public void shouldCorrectReturnAuthorityDto() {

        AuthorityDto authorityDto = authorityMapper.entityToDto(AUTHORITY_ENTITY);

        assertThat(authorityDto)
                .usingRecursiveComparison()
                .isEqualTo(AUTHORITY_DTO);
    }

    @Test
    @DisplayName("должен возарвщать ожидаемый список AuthorityDto")
    public void shouldCorrectReturnListAuthorityDto() {

        List<AuthorityDto> authorityDto = authorityMapper.entityToDto(List.of(AUTHORITY_ENTITY));

        assertThat(authorityDto)
                .usingRecursiveComparison()
                .isEqualTo(List.of(AUTHORITY_DTO));
    }
}
