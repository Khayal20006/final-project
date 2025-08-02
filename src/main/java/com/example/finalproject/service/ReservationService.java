package com.example.finalproject.service;

import com.example.finalproject.exception.RoomNotAvailableException;
import jakarta.transaction.Transactional;
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
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final CharacterEncodingFilter characterEncodingFilter;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> findAllByUserId(Long userId) {
        return reservationRepository.findAllByUserId(userId);
    }


    public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Reservation> overlappingReservations = reservationRepository
                .findReservationsThatOverlap(roomId, checkInDate, checkOutDate);
        return overlappingReservations.isEmpty();
    }
    @Transactional
    public void forceDeleteReservation(Long id) {
        Reservation reservation = findUserById(id);
        reservationRepository.delete(reservation);
    }
    public Reservation findUserById(Long id){
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }
    public boolean isRoomFree(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Reservation> reservations = reservationRepository.findReservationsThatOverlap(roomId, checkInDate, checkOutDate);
        return reservations.isEmpty();
    }
    @Transactional
    public ReservationDto createReservation(ReservationDto dto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        if (!isRoomAvailable(room.getId(), dto.getCheckInDate(), dto.getCheckOutDate())) {
            throw new RoomNotAvailableException("Room is not available for selected dates");
        }
        if (dto.getCheckInDate().isAfter(dto.getCheckOutDate()) || dto.getCheckInDate().isEqual(dto.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        double totalAmount = nights * room.getPrice();

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setTotalAmount(totalAmount);
        reservation.setStatus(Status.CONFIRMED);

        room.setIsAvailable(false);
        roomRepository.save(room);

        reservationRepository.save(reservation);

        return toDto(reservation);
    }

    @Transactional
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

    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        Room room = reservation.getRoom();

        reservation.setStatus(Status.CANCELLED);
        room.setIsAvailable(true);

        reservationRepository.save(reservation);
        roomRepository.save(room);
    }

    public ReservationDto toDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setCheckInDate(reservation.getCheckInDate());
        reservationDto.setCheckOutDate(reservation.getCheckOutDate());
        reservationDto.setUserId(reservation.getUser().getId());
        reservationDto.setRoomId(reservation.getRoom().getId());
        reservationDto.setTotalAmount(reservation.getTotalAmount());
        reservationDto.setStatus(reservation.getStatus());
        return reservationDto;
    }


}