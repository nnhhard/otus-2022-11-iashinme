package ru.iashinme.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iashinme.blog.dto.UserDto;
import ru.iashinme.blog.exception.ValidateException;
import ru.iashinme.blog.mapper.UserDtoMapper;
import ru.iashinme.blog.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userDtoMapper.entityToDto(userRepository.findAll());
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id).map(userDtoMapper::entityToDto).orElseThrow(
                () -> new ValidateException("User not found!")
        );
    }

    @Override
    @Transactional
    public UserDto userSetEnabled(Long userId, boolean enabled) {
        var user = userRepository.findById(userId).orElseThrow(
                () -> new ValidateException("User not found!")
        );

        user.setEnabled(enabled);
        return userDtoMapper.entityToDto(userRepository.save(user));
    }
}
