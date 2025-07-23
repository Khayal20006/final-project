package com.example.finalproject.service;

import com.example.finalproject.dto.RoomDto;
import com.example.finalproject.entity.Hotel;
import com.example.finalproject.entity.Room;
import com.example.finalproject.entity.enums.RoomType;
import com.example.finalproject.exception.HotelNotFoundException;
import com.example.finalproject.exception.RoomNotFoundException;
import com.example.finalproject.repository.HotelRepository;
import com.example.finalproject.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    public void addRooms(List<RoomDto> roomDtos) {
        List<Long> hotelIds = roomDtos.stream()
                .map(RoomDto::getHotelId)
                .toList();

        List<Hotel> hotels = hotelRepository.findAllById(hotelIds);

        Map<Long, Hotel> hotelMap = hotels.stream()
                .collect(Collectors.toMap(Hotel::getId, h -> h));

        for (RoomDto dto : roomDtos) {
            Room room = new Room();
            room.setRoomNumber(dto.getRoomNumber());
            room.setCapacity(dto.getCapacity());
            room.setPrice(dto.getPrice());
            room.setIsAvailable(dto.getIsAvailable());
            room.setType(dto.getRoomType());

            Hotel hotel = hotelMap.get(dto.getHotelId());
            if (hotel == null) {
                throw new HotelNotFoundException("Hotel not found id: " + dto.getHotelId());
            }
            room.setHotel(hotel);

            roomRepository.save(room);
        }
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found  id: " + id));
    }

    @Transactional
    public Room updateRoom(Long id, RoomDto roomDto) {
        Room room = getRoomById(id);

        room.setRoomNumber(roomDto.getRoomNumber());
        room.setCapacity(roomDto.getCapacity());
        room.setPrice(roomDto.getPrice());
        room.setIsAvailable(roomDto.getIsAvailable());
        room.setType(roomDto.getRoomType());

        Hotel hotel = hotelRepository.findById(roomDto.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found for id: " + roomDto.getHotelId()));
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
        Predicate<Room> roomPredicate =(r)->r.getCapacity()>=minCapacity&&r.getCapacity()<=maxCapacity;
        return getAllRooms().stream().filter(roomPredicate).toList();
    }

    public List<Room> findAvailableRoomsByDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        return roomRepository.findAvailableRoomsByDateRange(checkInDate, checkOutDate);
    }
}
