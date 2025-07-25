package com.example.finalproject.service;

import com.example.finalproject.dto.RoomDto;
import com.example.finalproject.entity.Hotel;
import com.example.finalproject.entity.Room;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final RoomMapper mapper;

    public List<RoomDto> findAvailableRoomsByHotel(Long hotelId) {
        List<Room> rooms = roomRepository.findAvailableRoomsByHotel(hotelId);
        return rooms.stream()
                .map(mapper::toDto)
                .toList();
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
                .distinct()
                .toList();

        Map<Long, Hotel> hotelMap = hotelRepository.findAllById(hotelIds).stream()
                .collect(Collectors.toMap(Hotel::getId, h -> h));

        List<Room> rooms = roomDtos.stream().map(dto -> {
            Room room = mapper.toEntity(dto);
            Hotel hotel = hotelMap.get(dto.getHotelId());
            if (hotel == null) {
                throw new HotelNotFoundException("Hotel not found for id: " + dto.getHotelId());
            }
            room.setHotel(hotel);
            return room;
        }).toList();

        roomRepository.saveAll(rooms);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found for id: " + id));
    }

    @Transactional
    public Room updateRoom(Long id, RoomDto roomDto) {
        Room room = getRoomById(id);

        mapper.updateRoomFromDto(roomDto, room);

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
        return getAllRooms().stream()
                .filter(r -> r.getCapacity() >= minCapacity
                        && r.getCapacity() <= maxCapacity)
                .toList();
    }

    public List<Room> findAvailableRoomsByDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        return roomRepository.findAvailableRoomsByDateRange(checkInDate, checkOutDate);
    }
}
