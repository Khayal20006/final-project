package com.example.finalproject.controller;

import com.example.finalproject.dto.ReservationDto;
import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.enums.Status;
import com.example.finalproject.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservation əməliyyatları", description = "Rezervasiya ilə bağlı API əməliyyatları")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @Operation(summary = "Bütün rezervasiyaları gətir")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "İstifadəçiyə görə rezervasiyaları gətir")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Status-a görə rezervasiyaları gətir")
    public ResponseEntity<List<Reservation>> getReservationsByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(reservationService.getReservationsByStatus(status));
    }

    @PostMapping
    @Operation(summary = "Yeni rezervasiya yarat")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto dto) {
        Reservation reservation = reservationService.createReservation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Rezervasiyanın statusunu dəyiş")
    public ResponseEntity<Reservation> updateReservationStatus(@PathVariable Long id, @RequestParam Status status) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(id, status));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Rezervasiyanı ləğv et")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byUser/{userId}")
    @Operation(summary = "Alternativ yolla istifadəçiyə görə rezervasiyaları gətir")
    public ResponseEntity<List<Reservation>> findAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.findAllByUserId(userId));
    }
}
