package ru.iashinme.blog.service;

import ru.iashinme.blog.dto.UserDto;

import java.util.List;

public interface SecurityService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto userSetEnabled(Long userId, boolean enabled);
}
