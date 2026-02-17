package com.example.demo.exceptions.custom;

public class InvalidArgumentException extends ValidationException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
