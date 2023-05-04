package ru.iashinme.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.UserMapper;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.model.User;
import ru.iashinme.blog.repository.AuthorityRepository;
import ru.iashinme.blog.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userMapper.entityToDto(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long userId) {
        return userMapper.entityToDto(findUserById(userId));
    }

    @Override
    @Transactional
    public UserDto userSetEnabled(Long userId, boolean enabled) {
        var user = findUserById(userId);

        user.setEnabled(enabled);

        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Authority> findAllAuthority() {
        return authorityRepository.findAll();
    }

    @Override
    @Transactional
    public UserDto userSetAuthority(Long userId, Long authorityId) {
        var user = findUserById(userId);

        var authority = authorityRepository.findById(authorityId).orElseThrow(
                () -> new ValidateException("Authority not found!")
        );

        user.setAuthority(authority);
        return userMapper.entityToDto(userRepository.save(user));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ValidateException("User not found!")
        );
    }
}
