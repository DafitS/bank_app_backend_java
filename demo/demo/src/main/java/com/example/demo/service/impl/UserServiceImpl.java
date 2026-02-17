package com.example.demo.service.impl;

import com.example.demo.dto.user.UserCreateDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.dto.user.UserUpdateDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.custom.UserNotExistExceptionById;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        User user = UserMapper.mapToUser(userCreateDto);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
            User user = userRepository
                    .findById(id)
                    .orElseThrow(() -> new UserNotExistExceptionById("User not found!", id));
            return UserMapper.mapToUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return  UserMapper.mapToUserResponseDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserUpdateDto dto) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotExistExceptionById("User not found!", id));

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getSurname() != null) user.setSurname(dto.getSurname());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPassword() != null) user.setPassword(dto.getPassword());

        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserResponseDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotExistExceptionById("User not found!", id));
        userRepository.delete(user);
    }


}
