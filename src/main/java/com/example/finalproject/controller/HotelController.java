package com.example.finalproject.controller;

import com.example.finalproject.dto.HotelDto;
import com.example.finalproject.entity.Hotel;
import com.example.finalproject.service.HotelService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel/")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/admin/createHotel")
    public Hotel createHotel(@RequestBody HotelDto hotelDto) {
        return hotelService.createHotel(hotelDto);
    }

    @GetMapping("/getAllHotels")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/getHotelByID/{id}")
    public Hotel getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PutMapping("/admin/updateHotel/{id}")
    public Hotel updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto) {
        return hotelService.updateHotel(id, hotelDto);
    }

    @DeleteMapping("/admin/deleteHotelById/{id}")
    public void deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }
}
