package com.example.demo.service;

import com.example.demo.dto.user.UserCreateDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.dto.user.UserUpdateDto;
import com.example.demo.option.RoleType;

public interface UserService {
    UserResponseDto createUser(UserCreateDto dto);

    UserResponseDto getUserById(Long id);

    UserResponseDto getUserByEmail(String email);

    UserResponseDto updateUser(Long id, UserUpdateDto dto);

    void changeUserRole(Long id, RoleType roleType);

    void deleteUser(Long id);
}
