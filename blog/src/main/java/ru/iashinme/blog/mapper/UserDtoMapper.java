package ru.iashinme.blog.mapper;

import org.springframework.stereotype.Component;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.dto.UserSmallDto;
import ru.iashinme.blog.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper {
    public List<UserDto> entityToDto(List<User> users) {
        return users.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public UserDto entityToDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .enabled(user.isEnabled())
                .build();
    }
}
