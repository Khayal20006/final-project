package com.example.finalproject.dto;

import com.example.finalproject.entity.Hotel;
import com.example.finalproject.entity.Reservation;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class RoomDto {
    private Long id;

    private Long roomNumber;

    private Integer capacity;

    private Double price;

    private Boolean isAvailable;

    private Long hotelId;

    private Long reservationId;

}
