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
    public List<Hotel>getAllHotels(){
        return hotelRepository.findAll();
    }
    @Transactional
    public Hotel createHotel(HotelDto hotelDto) {
        Hotel hotel = new Hotel();
        hotel.setAddress(hotelDto.getAddress());
        hotel.setName(hotelDto.getName());
        hotel.setRooms(new ArrayList<>());
        hotel.setStars(hotelDto.getStars());
        return hotelRepository.save(hotel);
    }
    public Hotel getHotelById(Long id){
       return hotelRepository.findById(id)
               .orElseThrow(()->new HotelNotFoundException("Hotel doesnt exist"));
    }
    @Transactional
    public Hotel updateHotel(Long id, HotelDto hotelDto){
        Hotel hotel = getHotelById(id);
        hotel.setName(hotelDto.getName());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setStars(hotelDto.getStars());
        return hotelRepository.save(hotel);
    }
    @Transactional
    public void deleteHotel(Long id){
        Hotel hotel = getHotelById(id);
        hotelRepository.delete(hotel);
        log.info("Hotel deleted from database successfully");
    }


}
