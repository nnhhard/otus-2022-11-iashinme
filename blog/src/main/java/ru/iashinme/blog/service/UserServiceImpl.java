package ru.iashinme.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.blog.dto.AuthDto;
import ru.iashinme.blog.dto.RegistrationDto;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.UserMapper;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.repository.AuthorityRepository;
import ru.iashinme.blog.repository.UserRepository;
import ru.iashinme.blog.security.JwtTokenProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthorityRepository authorityRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    @Transactional(readOnly = true)
    public UserDto authUser(AuthDto authDto, HttpServletResponse response) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
        User user = findByUsername(authDto.getUsername());
        String token = jwtTokenProvider.getJwtToken(user);
        Cookie cookie = new Cookie("userToken", token);
        cookie.setPath("/");
        cookie.setMaxAge(365 * 24 * 60 * 60);
        response.addCookie(cookie);

        return userMapper.entityToDto(user);
    }

    @Override
    @Transactional
    public void registrationUser(RegistrationDto registrationDto) {

        validate(registrationDto);

        if (Objects.nonNull(userRepository.findByUsername(registrationDto.getUsername()).orElse(null)))
            throw new ValidateException("Username is already taken.");

        var user = User.builder()
                .username(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .fullName(registrationDto.getFullName())
                .email(registrationDto.getEmail())
                .enabled(true)
                .authority(authorityRepository.findByAuthority("ROLE_USER"))
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User is not found!")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long userId) {
        var user =  userRepository.findById(userId).orElseThrow(
                () -> new ValidateException("User not found!")
        );

        return userMapper.entityToDto(user);
    }

    @Override
    public void removeToken(HttpServletResponse response) {
        Cookie cookie = new Cookie("userToken", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existAuthority() {
        return authorityRepository.existsBy();
    }

    private void validate(RegistrationDto registrationDto) {
        if (isEmpty(registrationDto.getUsername())) {
            throw new ValidateException("Username is null or empty.");
        }

        if (isEmpty(registrationDto.getPassword())) {
            throw new ValidateException("Password is null or empty.");
        }

        if (isEmpty(registrationDto.getEmail())) {
            throw new ValidateException("Email is null or empty.");
        }

        if (isEmpty(registrationDto.getFullName())) {
            throw new ValidateException("FullName is null or empty.");
        }
    }
}
