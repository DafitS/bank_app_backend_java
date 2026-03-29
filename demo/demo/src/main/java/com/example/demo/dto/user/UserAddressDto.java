package com.example.demo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDto {

    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String city;
    private String postalCode;
    private String country;
}
