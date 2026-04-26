package com.example.demo.mapper;

import com.example.demo.dto.user.UserAddressDto;
import com.example.demo.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressesMapper {

    Address toEntity (UserAddressDto userAddressDto);
    UserAddressDto toResponse (Address address);
}
