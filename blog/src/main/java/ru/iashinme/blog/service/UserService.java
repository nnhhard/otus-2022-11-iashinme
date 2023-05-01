package ru.iashinme.blog.service;

import ru.iashinme.blog.dto.AuthDto;
import ru.iashinme.blog.dto.RegistrationDto;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.dto.UserSmallDto;
import ru.iashinme.blog.model.User;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    UserDto authUser(AuthDto authDto, HttpServletResponse response);

    void registrationUser(RegistrationDto registrationDto);

    User findByUsername(String username);

    UserSmallDto findById(Long userId);

    void removeToken(HttpServletResponse response);
}
