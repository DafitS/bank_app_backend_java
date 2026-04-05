package com.example.demo.service;

import com.example.demo.dto.utility.RaportResponseDto;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;

public interface RaportService {

    RaportResponseDto generateRaport(Long accountId, LocalDateTime dateFrom, LocalDateTime dateTo);
}
