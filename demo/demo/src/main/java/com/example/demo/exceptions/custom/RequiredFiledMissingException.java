package com.example.demo.exceptions.custom;

public class RequiredFiledMissingException extends ValidationException {
    public RequiredFiledMissingException(String message) {
        super(message);
    }
}
