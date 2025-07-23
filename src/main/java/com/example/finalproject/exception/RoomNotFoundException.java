package com.example.finalproject.exception;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(String s) {
        super(s);
    }
}
