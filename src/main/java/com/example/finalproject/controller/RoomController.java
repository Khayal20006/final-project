package com.example.finalproject.controller;

import com.example.finalproject.dto.RoomDto;
import com.example.finalproject.entity.Room;
import com.example.finalproject.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;@RestController
@RequestMapping("/hotel/room")
@RequiredArgsConstructor
@Tag(description = "Roomlarla bağlı bütün əsas əməliyyatlar", name = "Room əməliyyatları")
public class RoomController {

    private final RoomService roomService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/findAllAvailableRoomsByHotelId/{hotelId}")
    public List<Room> findAvailableRoomsByHotel(@PathVariable Long hotelId) {
        return roomService.findAvailableRoomsByHotel(hotelId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/allAvailable")
    public List<Room> findAllAvailableRooms() {
        return roomService.findAllAvailableRooms();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Void> addRooms(@RequestBody List<RoomDto> roomDtos) {
        roomService.addRooms(roomDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        return roomService.updateRoom(id, roomDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/filter/byPrice")
    public List<Room> findByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return roomService.findByPriceRange(minPrice, maxPrice);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/filter/byCapacity")
    public List<Room> findRoomByCapacityRange(
            @RequestParam Integer minCapacity,
            @RequestParam Integer maxCapacity) {
        return roomService.findRoomByCapacityRange(minCapacity, maxCapacity);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/availableByDate")
    public List<Room> findAvailableRoomsByDateRange(
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        LocalDate checkIn = LocalDate.parse(checkInDate, formatter);
        LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);
        return roomService.findAvailableRoomsByDateRange(checkIn, checkOut);
    }
}
