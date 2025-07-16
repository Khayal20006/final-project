package com.example.finalproject.repository;

import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.User;
import com.example.finalproject.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId")
    List<Reservation> findAllByUserId(Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.status = :status")
    List<Reservation> findAllByStatus(Status status);

    @Query("SELECT r FROM Reservation r WHERE r.user =:user")
    List<Reservation> findAllReservationByUser(User user);
}
