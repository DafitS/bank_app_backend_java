package com.example.demo.security.domain;

import com.example.demo.entity.User;
import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {

    private final AccountRepository accountRepository;


    public boolean isOwner(Long userId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        User currentUser = (User) authentication.getPrincipal();

        if(currentUser.getId().equals(userId)) {
            return true;
        }

        return false;
    }
}
