package ru.iashinme.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.blog.dto.CustomUserDetails;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws ValidateException {
        return userRepository.findByUsername(username).map(CustomUserDetails::new)
                .orElseThrow(() -> new ValidateException("User is not found!"));
    }
}