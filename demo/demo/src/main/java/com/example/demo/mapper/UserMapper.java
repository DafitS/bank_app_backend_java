package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto dto) {
        User user = new User();
        user.setId(null);
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPesel(dto.getPesel());

        return user;
    }


    public static UserDto mapToUserDto(User user)
    {
        return new UserDto(
             user.getId(),
             user.getName(),
             user.getSurname(),
             user.getPesel()
        );
    }
}
