package com.example.demo.dto.utility;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class RaportResponseDto {

    private BigInteger income;

    private BigInteger expenses;

    private BigInteger balance;

}