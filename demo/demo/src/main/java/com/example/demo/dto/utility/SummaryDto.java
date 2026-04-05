package com.example.demo.dto.utility;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SummaryDto {

    private BigDecimal income;
    private BigDecimal expenses;
}