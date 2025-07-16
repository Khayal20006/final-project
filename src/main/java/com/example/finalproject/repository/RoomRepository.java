package com.example.finalproject.repository;

import com.example.finalproject.entity.Hotel;
import com.example.finalproject.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.reservation IS NULL AND r.isAvailable =true")
    List<Room> findAvailableRooms();

    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId AND r.reservation IS NULL")
    List<Room> findAvailableRoomsByHotel(Long hotelId);

    @Query("SELECT r FROM Room r WHERE " + "(:minPrice IS NULL OR r.price >= :minPrice) AND " + "(:maxPrice IS NULL OR r.price <= :maxPrice)")
    List<Room> findByPriceRange(Double minPrice, Double maxPrice);

    @Query("SELECT r from Room r WHERE " + "(:minCapacity IS NULL OR r.capacity >=:minCapacity) AND " +
            "(:maxCapacity IS NULL OR r.capacity<=:maxCapacity)")
    List<Room> findRoomByCapacityRange(Integer minCapacity, Integer maxCapacity);

    @Query("SELECT r FROM Room r WHERE r.isAvailable = true AND r.id NOT IN" +
            " (" + "SELECT res.room.id FROM Reservation res WHERE " +
            "(:checkInDate <= res.checkOutDate AND :checkOutDate >= res.checkInDate)"+")")
    List<Room> findAvailableRoomsByDateRange(LocalDate checkInDate, LocalDate checkOutDate);


}
