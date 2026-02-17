package com.example.demo.exceptions.custom;

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
