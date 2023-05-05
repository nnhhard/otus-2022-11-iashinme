package ru.iashinme.blog.rest;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.service.SecurityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/security")
@Hidden
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

    @GetMapping(value = "/authority")
    public List<Authority> findAllAuthority() {
        return securityService.findAllAuthority();
    }

    @PutMapping(value = "/user/{userId}/authority/{authorityId}")
    public UserDto editEnabled(@PathVariable Long userId, @PathVariable Long authorityId) {
        return securityService.userSetAuthority(userId, authorityId);
    }
}
