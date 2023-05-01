package ru.iashinme.blog.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.iashinme.blog.dto.AuthDto;
import ru.iashinme.blog.dto.RegistrationDto;
import ru.iashinme.blog.service.UserService;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthDto authDto, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(userService.authUser(authDto, response));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid login/password combination!");
        }
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<?> registrationUser(@RequestBody RegistrationDto registrationDto) {
        try {
            userService.registrationUser(registrationDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/logout")
    public void removeToken(HttpServletResponse response) {
        userService.removeToken(response);
    }
}
