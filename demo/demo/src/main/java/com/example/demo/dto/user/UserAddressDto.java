package com.example.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDto {

    @NotBlank(message = "Ulica nie może być pusta")
    private String street;

    @NotBlank(message = "Numer budynku nie może być pusty")
    private String buildingNumber;

    private String apartmentNumber;

    @NotBlank(message = "Miasto nie może być puste")
    private String city;

    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Kod pocztowy musi być w formacie __-___")
    private String postalCode;

    @NotBlank(message = "Kraj nie może być pusty")
    private String country;
}
