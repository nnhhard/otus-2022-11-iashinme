package ru.iashinme.blog.mapper;

import org.springframework.stereotype.Component;
import ru.iashinme.blog.dto.UserSmallDto;
import ru.iashinme.blog.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public List<UserSmallDto> entityToDto(List<User> users) {
        return users.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public UserSmallDto entityToDto(User user) {
        return UserSmallDto
                .builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }
}
