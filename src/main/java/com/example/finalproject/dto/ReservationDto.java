package com.example.finalproject.dto;

import com.example.finalproject.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {
    @Schema(hidden = true)
    private Long id;
    @Schema(hidden = true)
    private Status status;
    @Schema(hidden = true)
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    @Schema(hidden = true)
    private Double totalAmount;
}
