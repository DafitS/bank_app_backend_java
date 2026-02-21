package com.example.demo.controller;

import com.example.demo.dto.user.UserChangeRoleDto;
import com.example.demo.dto.user.UserCreateDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping

    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserCreateDto userCreateDto)
    {
        return new ResponseEntity<>(userService.createUser(userCreateDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CUSTOMER_SERVICE') or @userSecurity.isOwner(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id)
    {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyData(Authentication auth)
    {
        String email = auth.getName();
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}/role")
    public ResponseEntity<UserResponseDto> changeRole(@PathVariable Long id, @RequestBody UserChangeRoleDto userChangeRoleDto)
    {
        userService.changeUserRole(id, userChangeRoleDto.getRoleType());
        return ResponseEntity.noContent().build();
    }

}
