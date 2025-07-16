package com.example.finalproject.service;

import com.example.finalproject.dto.ReservationDto;
import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.Room;
import com.example.finalproject.entity.enums.Status;
import com.example.finalproject.entity.User;
import com.example.finalproject.repository.ReservationRepository;
import com.example.finalproject.repository.RoomRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Reservation createReservation(ReservationDto reservationDto) {
        User user = userRepository.findById(reservationDto.getUserId()).orElseThrow(() -> new RuntimeException("User not find"));
        Room room = roomRepository.findById(reservationDto.getRoomId()).orElseThrow(() -> new RuntimeException("Room not find"));
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setUser(user);
        room.setIsAvailable(false);
        reservation.setStatus(Status.PENDING);
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservationStatus(Long id, Status status) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }
    public List<Reservation> getReservationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findAllReservationByUser((user));
    }
    public List<Reservation> getReservationsByStatus(Status status) {
        return reservationRepository.findAllByStatus(status);
    }


    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Room room = reservation.getRoom();

        reservation.setStatus(Status.CANCELLED);
        room.setIsAvailable(true);

        reservationRepository.save(reservation);
        roomRepository.save(room);
    }

}