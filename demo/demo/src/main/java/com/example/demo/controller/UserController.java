package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto)
    {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id)
    {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMyData(Authentication auth)
    {
        String email = auth.getName();
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}
//klasa w controller authorize/authentication w sieczce metoda POST auth authorize w body pryjmuje email i password
//ta scezka zadeklarowana musi byc w konfiguracsi security onfiguration na white list i ta metoda musi zwrócic token jwt.
//wykorzyustac te implementacje zrobiona.
//Sprawdzic czy sie uruchamia i spróbowac wysłąc requesta do uwetrzytelnienia i powinien zwróvcic token.
