package com.example.finalproject.service;

import com.example.finalproject.dto.RoomDto;
import com.example.finalproject.entity.Hotel;
import com.example.finalproject.entity.Room;
import com.example.finalproject.repository.HotelRepository;
import com.example.finalproject.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public List<Room> findAvailableRoomsByHotel(Long hotelId) {
        return roomRepository.findAvailableRoomsByHotel(hotelId);
    }

    public List<Room> findAllAvailableRooms() {
        return roomRepository.findAvailableRooms();
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public Room addRoom(RoomDto roomDto) {
        Room room = new Room();
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setCapacity(roomDto.getCapacity());
        room.setPrice(roomDto.getPrice());
        room.setIsAvailable(roomDto.getIsAvailable());

        if (roomDto.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(roomDto.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomDto.getHotelId()));
            room.setHotel(hotel);
        }

        room.setReservation(null);
        return roomRepository.save(room);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    @Transactional
    public Room updateRoom(Long id, RoomDto roomDto) {
        Room room = getRoomById(id);

        room.setRoomNumber(roomDto.getRoomNumber());
        room.setCapacity(roomDto.getCapacity());
        room.setPrice(roomDto.getPrice());
        room.setIsAvailable(roomDto.getIsAvailable());
        Hotel hotel = hotelRepository.findById(roomDto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomDto.getHotelId()));
        room.setHotel(hotel);
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }

    public List<Room> findByPriceRange(Double minPrice, Double maxPrice) {
        return roomRepository.findByPriceRange(minPrice, maxPrice);
    }

    public List<Room> findRoomByCapacityRange(Integer minCapacity, Integer maxCapacity) {
        return roomRepository.findRoomByCapacityRange(minCapacity, maxCapacity);
    }

    public List<Room> findAvailableRoomsByDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        return roomRepository.findAvailableRoomsByDateRange(checkInDate, checkOutDate);
    }


}