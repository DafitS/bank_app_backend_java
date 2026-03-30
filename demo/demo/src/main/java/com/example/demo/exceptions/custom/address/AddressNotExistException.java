package com.example.demo.exceptions.custom.address;

public class AddressNotExistException extends RuntimeException {
    public AddressNotExistException(String message) {
        super(message);
    }
}
