package com.example.finalproject.mapper;

import com.example.finalproject.dto.HotelDto;
import com.example.finalproject.entity.Hotel;
import com.example.finalproject.entity.Room;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    Hotel toEntity(HotelDto dto);
    HotelDto toDto(Hotel hotel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateHotelFromDto(HotelDto dto, @MappingTarget Hotel hotel);
}
