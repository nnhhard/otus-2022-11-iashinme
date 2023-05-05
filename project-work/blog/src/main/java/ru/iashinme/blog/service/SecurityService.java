package ru.iashinme.blog.service;

import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.model.Authority;

import java.util.List;

public interface SecurityService {
    List<UserDto> findAll();

    UserDto findById(Long userId);

    UserDto userSetEnabled(Long userId, boolean enabled);

    List<Authority> findAllAuthority();

    UserDto userSetAuthority(Long userId, Long authorityId);
}
