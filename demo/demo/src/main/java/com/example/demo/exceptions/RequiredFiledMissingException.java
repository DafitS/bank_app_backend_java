package com.example.demo.exceptions;

public class RequiredFiledMissingException extends ValidationException {
    public RequiredFiledMissingException(String message) {
        super(message);
    }
}
