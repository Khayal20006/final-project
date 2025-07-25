package com.example.finalproject.service;
import org.springframework.security.core.Authentication;
import com.example.finalproject.dto.ReservationDto;
import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.Room;
import com.example.finalproject.entity.enums.Status;
import com.example.finalproject.entity.User;
import com.example.finalproject.exception.ReservationNotFoundException;
import com.example.finalproject.exception.RoomNotFoundException;
import com.example.finalproject.exception.UserNotFoundException;
import com.example.finalproject.repository.ReservationRepository;
import com.example.finalproject.repository.RoomRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    public List<Reservation> findAllByUserId(Long userId){
        return reservationRepository.findAllByUserId(userId);
    }

    public Reservation createReservation(ReservationDto dto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        double totalAmount = nights * room.getPrice();

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setTotalAmount(totalAmount);
        reservation.setStatus(Status.PENDING);

        room.setIsAvailable(false);
        roomRepository.save(room);
        return reservationRepository.save(reservation);
    }


    public Reservation updateReservationStatus(Long id, Status status) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }
    public List<Reservation> getReservationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return reservationRepository.findAllReservationByUser((user));
    }
    public List<Reservation> getReservationsByStatus(Status status) {
        return reservationRepository.findAllByStatus(status);
    }


    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        Room room = reservation.getRoom();

        reservation.setStatus(Status.CANCELLED);
        room.setIsAvailable(true);

        reservationRepository.save(reservation);
        roomRepository.save(room);
    }

}