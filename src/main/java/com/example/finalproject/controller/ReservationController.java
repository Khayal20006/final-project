package com.example.finalproject.controller;

import com.example.finalproject.dto.ReservationDto;
import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.enums.Status;
import com.example.finalproject.exception.ReservationNotFoundException;
import com.example.finalproject.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/hotel/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservation əməliyyatları", description = "Rezervasiya ilə bağlı API əməliyyatları")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/get-All-Reservations")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Bütün rezervasiyaları gətir")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/user/{userId}")
    @Operation(summary = "İstifadəçiyə görə rezervasiyaları gətir")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{status}")
    @Operation(summary = "Status-a görə rezervasiyaları gətir")
    public ResponseEntity<List<Reservation>> getReservationsByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(reservationService.getReservationsByStatus(status));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/create-reservation")
    @Operation(summary = "Yeni rezervasiya yarat")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto dto, Authentication authentication) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate checkInDate = dto.getCheckInDate();
        LocalDate checkOutDate =dto.getCheckOutDate();
        boolean isAvailable = reservationService.isRoomAvailable(dto.getRoomId(), checkInDate, checkOutDate);

        if (isAvailable) {
            ReservationDto reservation = reservationService.createReservation(dto, authentication);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/reservasiyani sil")
    @Operation(summary = "Reservationlari sil yalniz admin")
    public void deleteReservation(Long id) {
        reservationService.forceDeleteReservation(id);
    }




    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    @Operation(summary = "Rezervasiyanın statusunu dəyiş")
    public ResponseEntity<Reservation> updateReservationStatus(@PathVariable Long id, @RequestParam Status status) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(id, status));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}/cancel")
    @Operation(summary = "Rezervasiyanı ləğv et")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

}
