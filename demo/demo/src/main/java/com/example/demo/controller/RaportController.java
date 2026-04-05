package com.example.demo.controller;

import com.example.demo.dto.utility.RaportRequestDto;
import com.example.demo.dto.utility.RaportResponseDto;
import com.example.demo.service.RaportService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/raport")

public class RaportController {
    private final RaportService raportService;

    public RaportController(RaportService raportService) {
        this.raportService = raportService;
    }
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CUSTOMER_SERVICE') or @accountSecurity.isOwner(#accountId)")
    @PostMapping("/generate/{accountId}")
    public ResponseEntity<RaportResponseDto> generateFinancialReport(
            @PathVariable Long accountId,
            @Valid @RequestBody RaportRequestDto dto
    ) {
        RaportResponseDto response = raportService.generateRaport(
                accountId,
                dto.getDateFrom(),
                dto.getDateTo()
        );

        return ResponseEntity.ok(response);
    }

}

