package com.example.demo.service;

import com.example.demo.dto.user.UserAddressDto;
import com.example.demo.dto.user.UserResponseDto;

import java.util.UUID;

public interface AddressService {

    UserAddressDto getAddressByPublicId(UUID id);

    UserResponseDto createNewUserAddress(Long id, UserAddressDto userAddressDto);

    UserAddressDto updateAddress(UUID id, UserAddressDto userAddressDto);
}
