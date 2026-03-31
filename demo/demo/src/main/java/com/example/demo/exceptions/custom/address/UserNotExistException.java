package com.example.demo.exceptions.custom.address;

import com.example.demo.exceptions.custom.ValidationException;

public class UserNotExistException extends ValidationException {
    public UserNotExistException(String message, String userName) {
        super(message);
        this.userName = userName;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }


}
