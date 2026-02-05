package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAppApplication.class, args);
	}

}

//HTTP Request
//   ↓
//Controller
//   ↓
//Service
//   ↓
//Repository
//   ↓
//Database
//   ↑
//Entity
//   ↑
//Service
//   ↑
//DTO
//   ↑
//Controller
//   ↑
//HTTP Response

// Dorobic obsluge wjatkow, dodac tabele z historia np wplata wypłata, data, typ(enum) itp, pod filtracje danych, logowanie log2j, funkcja logowania pod jwt