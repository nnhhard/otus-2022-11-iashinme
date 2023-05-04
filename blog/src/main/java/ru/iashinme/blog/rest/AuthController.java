package ru.iashinme.blog.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
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

    @Operation(summary = "Authentication user")
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthDto authDto, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(userService.authUser(authDto, response));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is block!");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid login/password combination!");
        }
    }

    @Operation(summary = "Registration user")
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

    @Operation(summary = "Logout user")
    @DeleteMapping(value = "/logout")
    public void removeToken(HttpServletResponse response) {
        userService.removeToken(response);
    }
}
