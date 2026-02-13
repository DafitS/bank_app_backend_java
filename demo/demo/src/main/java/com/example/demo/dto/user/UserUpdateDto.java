package com.example.demo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private String name;
    private String surname;

    @Email(message = "Niepoprawny email")
    private String email;

    @Size(min = 8, message = "Hasło musi mieć minimum 8 znaków")
    private String password;
}