package com.example.demo.dto.type;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExpenseCreateDto {

    @NotBlank
    private String name;
}
