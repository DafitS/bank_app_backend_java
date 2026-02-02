package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.*;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;
    private String accountHolderName;
    private String accountNumber;
    private Double amount;
    private Long userId;


}