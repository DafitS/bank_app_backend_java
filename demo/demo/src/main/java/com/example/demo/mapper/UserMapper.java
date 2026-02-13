package com.example.demo.mapper;

import com.example.demo.dto.user.UserCreateDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.entity.User;

public class UserMapper {

    public static User mapToUser(UserCreateDto dto) {
        User user = new User();
        user.setId(null);
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPesel(dto.getPesel());
        return user;
    }

    public static UserResponseDto mapToUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPesel()
        );
    }
}