package com.example.demo.exceptions.custom;

public class DuplicateError extends ValidationException {
    public DuplicateError(String message) {
        super(message);
    }
}
