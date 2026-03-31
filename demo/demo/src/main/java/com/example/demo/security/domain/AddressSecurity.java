package com.example.demo.security.domain;


import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("addressSecurity")
@RequiredArgsConstructor
public class AddressSecurity {

    private final AddressRepository addressRepository;

    public boolean isOwner(UUID addressId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        return addressRepository
                .findByPublicId(addressId)
                    .map(address -> address.getUser().getId().equals(currentUser.getId()))
                        .orElse(false);

    }
}
