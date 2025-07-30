package com.example.finalproject.repository;

import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.User;
import com.example.finalproject.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId")
    List<Reservation> findAllByUserId(Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.status = :status")
    List<Reservation> findAllByStatus(Status status);

    @Query("SELECT r FROM Reservation r WHERE r.user =:user")
    List<Reservation> findAllReservationByUser(User user);

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId " +
            "AND r.status != 'CANCELLED' " +
            "AND r.checkInDate < :checkOutDate " +
            "AND r.checkOutDate > :checkInDate")
    List<Reservation> findReservationsThatOverlap(@Param("roomId") Long roomId,
                                                  @Param("checkInDate") LocalDate checkInDate,
                                                  @Param("checkOutDate") LocalDate checkOutDate);
}
