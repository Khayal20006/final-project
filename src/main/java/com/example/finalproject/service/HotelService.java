package com.example.finalproject.service;

import com.example.finalproject.dto.HotelDto;
import com.example.finalproject.entity.Hotel;
import com.example.finalproject.exception.HotelNotFoundException;
import com.example.finalproject.repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    public List<HotelDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public HotelDto createHotel(HotelDto hotelDto) {
        log.info("Received HotelDto: {}", hotelDto);

        Hotel hotel = new Hotel();
        hotel.setName(hotelDto.getName());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setStars(hotelDto.getStars());
        hotel.setRooms(new ArrayList<>());

        log.info("Created Hotel entity: {}", hotel);

        Hotel savedHotel = hotelRepository.save(hotel);
        log.info("Saved Hotel entity: {}", savedHotel);

        return toDto(savedHotel);
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel doesnt exist"));
    }

    @Transactional
    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        Hotel hotel = getHotelById(id);

        if (hotelDto.getName() != null) {
            hotel.setName(hotelDto.getName());
        }
        if (hotelDto.getAddress() != null) {
            hotel.setAddress(hotelDto.getAddress());
        }
        if (hotelDto.getStars() != null) {
            hotel.setStars(hotelDto.getStars());
        }

        Hotel updatedHotel = hotelRepository.save(hotel);
        return toDto(updatedHotel);
    }

    @Transactional
    public void deleteHotel(Long id) {
        Hotel hotel = getHotelById(id);
        hotelRepository.delete(hotel);
        log.info("Hotel deleted from database successfully");
    }

    private HotelDto toDto(Hotel hotel) {
        HotelDto dto = new HotelDto();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setAddress(hotel.getAddress());
        dto.setStars(hotel.getStars());
        return dto;
    }
}