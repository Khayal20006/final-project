package com.example.finalproject.service;

import com.example.finalproject.dto.HotelDto;
import com.example.finalproject.entity.Hotel;
import com.example.finalproject.exception.HotelNotFoundException;
import com.example.finalproject.mapper.HotelMapper;
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
    private final HotelMapper mapper;

    public List<HotelDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public HotelDto createHotel(HotelDto hotelDto) {
        Hotel hotel = mapper.toEntity(hotelDto);
        hotel.setRooms(new ArrayList<>());
        Hotel savedHotel = hotelRepository.save(hotel);
        return mapper.toDto(savedHotel);
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel doesnt exist"));
    }


    @Transactional
    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        Hotel hotel = getHotelById(id);
        mapper.updateHotelFromDto(hotelDto, hotel);
        Hotel updatedHotel = hotelRepository.save(hotel);
        return mapper.toDto(updatedHotel);
    }

    @Transactional
    public void deleteHotel(Long id) {
        Hotel hotel = getHotelById(id);
        hotelRepository.delete(hotel);
        log.info("Hotel deleted from database successfully");
    }
}
