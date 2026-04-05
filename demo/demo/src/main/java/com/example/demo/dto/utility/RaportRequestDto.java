package com.example.demo.dto.utility;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RaportRequestDto {

    @NotNull(message = "Date from cannot be null")
    private LocalDateTime dateFrom;

    @NotNull(message = "Date to cannot be null")
    private LocalDateTime dateTo;

}