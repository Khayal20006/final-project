package com.example.finalproject.dto;

import com.example.finalproject.entity.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Long id;
    private Long reservationId;
    private Double amount;
    private String transactionId;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
}
