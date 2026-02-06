package com.example.demo.exceptions;

public class UserNotExistExceptionById extends RuntimeException {
    public UserNotExistExceptionById(String message, Long id) {
        super(message);
        this.id = id;
    }
    private Long id;

    public Long getId() {
        return id;
    }
}
