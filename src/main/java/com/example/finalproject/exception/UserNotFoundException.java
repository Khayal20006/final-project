package com.example.finalproject.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
