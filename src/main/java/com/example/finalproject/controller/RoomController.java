package com.example.finalproject.controller;

import com.example.finalproject.dto.RoomDto;
import com.example.finalproject.entity.Room;
import com.example.finalproject.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Tag(name = "Room Controller", description = "Otaq əməliyyatları")
public class RoomController {

    private final RoomService roomService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/available/hotel/{hotelId}")
    @Operation(summary = "Hotelə görə mövcud otaqları gətir")
    public ResponseEntity<List<RoomDto>> getAvailableRoomsByHotel(@PathVariable Long hotelId) {
        List<RoomDto> rooms = roomService.findAvailableRoomsByHotel(hotelId);
        return ResponseEntity.ok(rooms);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/all-available")
    @Operation(summary = "Bütün mövcud otaqları gətir")
    public ResponseEntity<List<Room>> getAllAvailableRooms() {
        List<Room> rooms = roomService.findAllAvailableRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Bütün otaqları gətir")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Yeni otaqlar əlavə et")
    public ResponseEntity<Void> addRooms(@RequestBody List<RoomDto> roomDtos) {
        roomService.addRooms(roomDtos);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Otağı ID ilə gətir")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Otağı yenilə")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        Room updatedRoom = roomService.updateRoom(id, roomDto);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Otağı sil")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/price")
    @Operation(summary = "Qiymət aralığına görə otaqları gətir")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")

    public ResponseEntity<List<Room>> findRoomsByPriceRange(@RequestParam Double minPrice,
                                                            @RequestParam Double maxPrice) {
        List<Room> rooms = roomService.findByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/search/capacity")
    @Operation(summary = "Tutuma görə otaqları gətir")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Room>> findRoomsByCapacityRange(@RequestParam Integer minCapacity,
                                                               @RequestParam Integer maxCapacity) {
        List<Room> rooms = roomService.findRoomByCapacityRange(minCapacity, maxCapacity);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/search/availability")
    @Operation(summary = "Tarix aralığında mövcud otaqları gətir")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Room>> findAvailableRoomsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkInDate,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkOutDate) {

        List<Room> rooms = roomService.findAvailableRoomsByDateRange(checkInDate, checkOutDate);
        return ResponseEntity.ok(rooms);
    }
}
