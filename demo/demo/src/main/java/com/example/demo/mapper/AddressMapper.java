package com.example.demo.mapper;

import com.example.demo.dto.user.UserAddressDto;
import com.example.demo.entity.Address;

public class AddressMapper {

    public static Address mapToEntity(UserAddressDto dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setBuildingNumber(dto.getBuildingNumber());
        address.setApartmentNumber(dto.getApartmentNumber());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());

        return address;
    }

    public static UserAddressDto mapToDto(Address address) {
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

    public static void partialUpdateAddress(Address address, UserAddressDto dto) {
        if (address == null || dto == null) return;

        if (dto.getStreet() != null) address.setStreet(dto.getStreet());
        if (dto.getBuildingNumber() != null) address.setBuildingNumber(dto.getBuildingNumber());
        if (dto.getApartmentNumber() != null) address.setApartmentNumber(dto.getApartmentNumber());
        if (dto.getCity() != null) address.setCity(dto.getCity());
        if (dto.getPostalCode() != null) address.setPostalCode(dto.getPostalCode());
        if (dto.getCountry() != null) address.setCountry(dto.getCountry());
    }
}