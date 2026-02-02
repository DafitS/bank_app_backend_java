package com.example.demo.exceptions;

public class UserNotExistExceptions extends ValidationExceptions {
    public UserNotExistExceptions(String message, String userName) {
        super(message);
        this.userName = userName;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }


}
