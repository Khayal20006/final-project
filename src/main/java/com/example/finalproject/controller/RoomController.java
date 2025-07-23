package com.example.finalproject.controller;

import com.example.finalproject.dto.RoomDto;
import com.example.finalproject.entity.Room;
import com.example.finalproject.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/hotel/room")
@RequiredArgsConstructor
@Tag(description = "Roomlarla bagli butun esas emeliyyatlar",name ="Room emeliyyatlari")
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "ID üzrə oteldəki boş otaqları tapır")
    @GetMapping("/admin/findAllAvailableRoomsByHotelId/{hotelId}")
    public List<Room> findAvailableRoomsByHotel(
            @Parameter(description = "Otelin ID-si") @PathVariable Long hotelId) {
        return roomService.findAvailableRoomsByHotel(hotelId);
    }

    @Operation(summary = "Sistemdəki bütün boş otaqları qaytarır")
    @GetMapping("/allAvailable")
    public List<Room> findAllAvailableRooms() {
        return roomService.findAllAvailableRooms();
    }

    @Operation(summary = "Bütün otaqları qaytarır")
    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @Operation(summary = "Otaqları əlavə edir (admin üçün)")
    @PostMapping("/add")
    public void addRooms(@RequestBody List<RoomDto> roomDtos) {
        roomService.addRooms(roomDtos);
    }

    @Operation(summary = "ID-yə görə otaq tapır")
    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @Operation(summary = "Otaq məlumatını yeniləyir (admin üçün)")
    @PutMapping("/update/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        return roomService.updateRoom(id, roomDto);
    }

    @Operation(summary = "ID-yə görə otağı silir (admin üçün)")
    @DeleteMapping("/delete/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }

    @Operation(summary = "Qiymət aralığına görə otaqları filtrləyir")
    @GetMapping("/filter/byPrice")
    public List<Room> findByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return roomService.findByPriceRange(minPrice, maxPrice);
    }

    @Operation(summary = "Capacitye görə otaqları filtrləyir")
    @GetMapping("/filter/byCapacity")
    public List<Room> findRoomByCapacityRange(
            @RequestParam Integer minCapacity,
            @RequestParam Integer maxCapacity) {
        return roomService.findRoomByCapacityRange(minCapacity, maxCapacity);
    }

    @Operation(summary ="Capacitye görə mövcud otaqları tapır")
    @GetMapping("/availableByDate")
    public List<Room> findAvailableRoomsByDateRange(
            @Parameter(description = "Check in tarixi  məsələn: 12-5-2025") @RequestParam String checkInDate,
            @Parameter(description = "Check out tarixi  məsələn: 15-5-2025") @RequestParam String checkOutDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        LocalDate checkIn = LocalDate.parse(checkInDate, formatter);
        LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);

        return roomService.findAvailableRoomsByDateRange(checkIn, checkOut);
    }
}
