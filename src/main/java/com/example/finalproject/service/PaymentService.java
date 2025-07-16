package com.example.finalproject.service;

import com.example.finalproject.dto.PaymentDto;
import com.example.finalproject.entity.Payment;
import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.enums.PaymentStatus;
import com.example.finalproject.entity.enums.Status;
import com.example.finalproject.repository.PaymentRepository;
import com.example.finalproject.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    @Transactional
    public Payment createPayment(Long reservationId) {
        PaymentDto paymentDto = new PaymentDto();
        Reservation reservation = reservationRepository.findById(paymentDto.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setAmount(paymentDto.getAmount());

        payment.setPaymentStatus(PaymentStatus.PENDING);

        payment.setPaymentDate(paymentDto.getPaymentDate() != null ? paymentDto.getPaymentDate()
                : LocalDateTime.now());

        payment.setTransactionId(paymentDto.getTransactionId() != null ? paymentDto.getTransactionId()
                : UUID.randomUUID().toString());
        reservation.setStatus(Status.CONFIRMED);
        reservationRepository.save(reservation);
        return paymentRepository.save(payment);

    }
}
