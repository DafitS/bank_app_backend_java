package com.example.demo.mapper;

import com.example.demo.dto.user.UserAddressDto;
import com.example.demo.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressesMapper {

    Address toEntity (UserAddressDto userAddressDto);
    UserAddressDto toResponse (Address address);
    void updateAddress(UserAddressDto dto, @MappingTarget Address address);
}
