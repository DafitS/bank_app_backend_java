package com.example.demo.dto.user;

import com.example.demo.option.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserChangeRoleDto {

    @NotNull
    private RoleType roleType;
}