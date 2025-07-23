package com.example.finalproject.exception;

public class ReservationNotFoundException extends RuntimeException{

    public ReservationNotFoundException(String reservationNotFound) {
        super(reservationNotFound);
    }
}
