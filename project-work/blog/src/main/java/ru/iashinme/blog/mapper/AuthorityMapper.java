package ru.iashinme.blog.mapper;

import org.springframework.stereotype.Component;
import ru.iashinme.blog.dto.AuthorityDto;
import ru.iashinme.blog.model.Authority;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorityMapper {
    public List<AuthorityDto> entityToDto(List<Authority> authorities) {
        return authorities.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public AuthorityDto entityToDto(Authority authority) {
        return new AuthorityDto(authority.getId(), authority.getAuthority());
    }
}
