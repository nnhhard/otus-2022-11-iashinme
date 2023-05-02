package ru.iashinme.blog.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.service.SecurityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/security")
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping(value = "/user")
    public List<UserDto> findAll() {
        return securityService.findAll();
    }

    @GetMapping(value = "/user/{id}")
    public UserDto findById(@PathVariable Long id) {
        return securityService.findById(id);
    }

    @PutMapping(value = "/user/{id}/enabled/{enabled}")
    public UserDto editEnabled(@PathVariable Long id, @PathVariable boolean enabled) {
        return securityService.userSetEnabled(id, enabled);
    }
}
