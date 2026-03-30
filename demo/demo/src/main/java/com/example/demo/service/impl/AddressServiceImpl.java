package com.example.demo.service.impl;

import com.example.demo.dto.user.UserAddressDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.exceptions.custom.UserNotExistExceptionById;
import com.example.demo.exceptions.custom.address.AddressNotExistException;
import com.example.demo.mapper.AddressMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;


    @Override
    public UserAddressDto getAddressByPublicId(UUID id) {

        Address address = addressRepository
                .findByPublicId(id)
                    .orElseThrow(() -> new AddressNotExistException("Address not found"));

        return UserMapper.mapToUserAddressDto(address);
    }

    @Override
    public UserResponseDto createNewUserAddress(Long id, UserAddressDto userAddressDto) {
        User user = userRepository
                .findById(id)
                    .orElseThrow(()-> new UserNotExistExceptionById("User not found", id));

        user.getAddresses().forEach(address -> address.setIsCurrent(false));

        Address newAddress = AddressMapper.mapToEntity(userAddressDto);
        newAddress.setUser(user);
        newAddress.setIsCurrent(true);

        addressRepository.save(newAddress);

        return UserMapper.mapToUserResponseDto(user);


    }

    @Override
    public UserAddressDto updateAddress(UUID id, UserAddressDto userAddressDto) {
        Address address = addressRepository
                .findByPublicId(id)
                    .orElseThrow(() -> new AddressNotExistException("Address not found"));

        AddressMapper.partialUpdateAddress(address, userAddressDto);

        Address updatedAddress = addressRepository.save(address);

        return AddressMapper.mapToDto(updatedAddress);
    }
}
