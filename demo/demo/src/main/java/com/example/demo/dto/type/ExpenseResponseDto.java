package com.example.demo.dto.type;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseResponseDto {

    private Long id;
    private String name;
    private boolean active;
}
