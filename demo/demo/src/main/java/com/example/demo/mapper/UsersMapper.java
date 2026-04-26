package com.example.demo.mapper;

import com.example.demo.dto.user.UserAddressDto;
import com.example.demo.dto.user.UserCreateDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.control.MappingControl;

@Mapper(componentModel = "spring", uses = AddressesMapper.class)
public interface UsersMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    User toEntity(UserCreateDto userCreateDto);

    @Mapping(target = "userAddress", expression = "java(getCurrentAddress(user))")
    UserResponseDto toDto(User user);

    default UserAddressDto getCurrentAddress(User user) {
        return user.getAddresses()
                .stream()
                .filter(Address::getIsCurrent)
                .findFirst()
                .map(this::toAddressDto)
                .orElse(null);
    }

    Address toAddress(UserAddressDto dto);
    UserAddressDto toAddressDto(Address address);

    @AfterMapping
    default void addAddress(UserCreateDto dto, @MappingTarget User user) {
        if (dto.getUserAddress() != null) {
            Address address = toAddress(dto.getUserAddress());
            address.setUser(user);
            address.setIsCurrent(true);
            user.getAddresses().add(address);
        }
    }
}
