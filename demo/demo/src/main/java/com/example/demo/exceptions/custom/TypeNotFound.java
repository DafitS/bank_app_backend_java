package com.example.demo.exceptions.custom;

public class TypeNotFound extends ValidationException {
    public TypeNotFound(String message) {
        super(message);
    }
}
