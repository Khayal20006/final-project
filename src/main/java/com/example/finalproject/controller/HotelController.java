package com.example.finalproject.controller;

import com.example.finalproject.dto.HotelDto;
import com.example.finalproject.entity.Hotel;
import com.example.finalproject.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/hotel/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

@Tag(name = "Hotel Controller", description = "Hotel ilə bağlı əməliyyatlar")
public class HotelController {
    private final HotelService hotelService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/createHotel")
    @Operation(summary = "Yeni hotel yarat")

    public HotelDto createHotel( @RequestBody HotelDto hotelDto) {
        return hotelService.createHotel(hotelDto);
    }
    @GetMapping("/getAllHotels")
    @Operation(summary = "Bütün hotelləri gətir", description = "Sistemdə mövcud olan bütün hotelləri göstərir")
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/getHotelByID/{id}")
    @Operation(summary = "Hotelleri sadece id leri yazmaqla getir")
    public Hotel getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

   @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/updateHotel/{id}")
    @Operation(summary = "Hoteli id ilə yenilə", description = "id-ni yazmaqla hoteli yeniləmək")
    public HotelDto updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto) {
        return hotelService.updateHotel(id, hotelDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/deleteHotelById/{id}")
    @Operation(summary = "Hotel sil", description = "ID-yə uyğun hotel silinir")
    public void deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }
}

