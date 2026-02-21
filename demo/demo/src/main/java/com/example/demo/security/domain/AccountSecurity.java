package com.example.demo.security.domain;

import com.example.demo.entity.User;
import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("accountSecurity")
@RequiredArgsConstructor
public class AccountSecurity {
    private final AccountRepository accountRepository;

    public boolean isOwner(Long accountId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        return accountRepository.findById(accountId)
                .map(account -> account.getUser().getId().equals(currentUser.getId()))
                .orElse(false);
    }
}
