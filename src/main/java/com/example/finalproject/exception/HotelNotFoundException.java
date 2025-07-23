package com.example.finalproject.exception;

public class HotelNotFoundException extends RuntimeException{
    public HotelNotFoundException(String hotelDoesntExist) {
       super(hotelDoesntExist);
    }
}
