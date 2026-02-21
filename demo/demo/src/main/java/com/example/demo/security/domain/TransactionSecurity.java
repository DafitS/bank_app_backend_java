package com.example.demo.security.domain;

import com.example.demo.entity.User;
import com.example.demo.option.RoleType;
import com.example.demo.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("transactionSecurity")
@RequiredArgsConstructor
public class TransactionSecurity {

    private final TransactionRepository transactionRepository;

    public boolean isOwner(Long transactionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication.getPrincipal() instanceof User currentUser)) {
            return false;
        }


        if(currentUser.getRoleType() == RoleType.ADMINISTRATOR){
            return true;
        }

        return transactionRepository.findById(transactionId)
                .map(transaction -> transaction.getAccountFrom()
                        .getUser().getId().equals(currentUser.getId()))
                            .orElse(false);
    }
}
