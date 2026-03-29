package com.example.demo.mapper;

import com.example.demo.dto.user.UserAddressDto;
import com.example.demo.dto.user.UserCreateDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    private static UserAddressDto mapToUserAddressDto(Address address) {
        if (address == null) return null;
        UserAddressDto dto = new UserAddressDto();
        dto.setStreet(address.getStreet());
        dto.setBuildingNumber(address.getBuildingNumber());
        dto.setApartmentNumber(address.getApartmentNumber());
        dto.setCity(address.getCity());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        return dto;
    }

    public static User mapToUser(UserCreateDto dto) {
        User user = new User();
        user.setId(null);
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPesel(dto.getPesel());
        user.setRoleType(dto.getRoleType());

        if (dto.getUserAddress() != null) {

            Address address = new Address();
            address.setStreet(dto.getUserAddress().getStreet());
            address.setBuildingNumber(dto.getUserAddress().getBuildingNumber());
            address.setApartmentNumber(dto.getUserAddress().getApartmentNumber());
            address.setCity(dto.getUserAddress().getCity());
            address.setPostalCode(dto.getUserAddress().getPostalCode());
            address.setCountry(dto.getUserAddress().getCountry());
            address.setUser(user);
            user.getAddresses().add(address);
        }
        return user;
    }

    public static UserResponseDto mapToUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPesel(),
                null
        );

        Address currentAddressFromUser = (user.getAddresses() != null ? user.getAddresses() : new ArrayList<Address>())
                .stream()
                .filter(Address::getIsCurrent)
                .findFirst()
                .orElse(null);

        dto.setUserAddress(mapToUserAddressDto(currentAddressFromUser));

        return dto;


    }
}