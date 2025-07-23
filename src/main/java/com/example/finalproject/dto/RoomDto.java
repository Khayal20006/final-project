package com.example.finalproject.dto;

import com.example.finalproject.entity.Hotel;
import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class RoomDto {
    @Schema(hidden = true)
    private Long id;

    private Long roomNumber;

    private Integer capacity;

    private Double price;

    private Boolean isAvailable;

    @NotNull(message = "Hotel ID mütləqdir cunki hansi hotele hansi roomu elave edirsen onu bilmeliyik")
    private Long hotelId;

    private Long reservationId;

    private RoomType roomType;

}
