package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;
    private String accountHolderName;
    private String accountNumber;
    private BigDecimal amount;
    private Long userId;


}