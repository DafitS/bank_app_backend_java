package com.example.demo.controller;


import com.example.demo.dto.user.UserAddressDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/users/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{addressId}")
    public ResponseEntity<UserAddressDto> getAddress(@PathVariable UUID addressId) {
        UserAddressDto dto = addressService.getAddressByPublicId(addressId);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addNewAddress(
            @Valid @RequestBody UserAddressDto userAddressDto, @RequestParam Long userId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.createNewUserAddress(userId, userAddressDto));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<UserAddressDto> updateAddress(
            @PathVariable UUID addressId,
            @Valid @RequestBody UserAddressDto dto) {

        return ResponseEntity.ok(addressService.updateAddress(addressId, dto));
    }

}
