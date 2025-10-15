package com.example.finalproject.dto;

import com.example.finalproject.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;

    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDate checkOutDate;

    @Schema(hidden = true)
    private Double totalAmount;
}
