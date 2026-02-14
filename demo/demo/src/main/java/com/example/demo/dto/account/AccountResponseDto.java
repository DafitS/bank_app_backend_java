package com.example.demo.dto.account;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {

    private String accountHolderName;
    private String accountNumber;
    private BigDecimal amount;
}
