package com.example.finalproject.controller;

import com.example.finalproject.dto.RoomDto;
import com.example.finalproject.entity.Room;
import com.example.finalproject.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hotel/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/admin/findAllAvailableRoomsByHotelId/{hotelId}")
    public List<Room> findAvailableRoomsByHotel(@PathVariable Long hotelId) {
        return roomService.findAvailableRoomsByHotel(hotelId);
    }

    @GetMapping("/allAvailable")
    public List<Room> findAllAvailableRooms() {
        return roomService.findAllAvailableRooms();
    }

    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/add")
    public Room addRoom(@RequestBody RoomDto roomDto) {
        return roomService.addRoom(roomDto);
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PutMapping("/update/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        return roomService.updateRoom(id, roomDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }

    @GetMapping("/filter/byPrice")
    public List<Room> findByPriceRange(@RequestParam Double minPrice,@RequestParam Double maxPrice) {
        return roomService.findByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/filter/byCapacity")
    public List<Room> findRoomByCapacityRange(@RequestParam Integer minCapacity,@RequestParam Integer maxCapacity) {
        return roomService.findRoomByCapacityRange(minCapacity, maxCapacity);
    }

    @GetMapping("/availableByDate")
    public List<Room> findAvailableRoomsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
            return roomService.findAvailableRoomsByDateRange(checkInDate, checkOutDate);
    }
}
