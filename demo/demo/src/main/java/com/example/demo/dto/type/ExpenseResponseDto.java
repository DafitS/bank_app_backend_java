package com.example.demo.dto.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class ExpenseResponseDto {

    private Long id;
    private String name;
    private boolean active;
}
