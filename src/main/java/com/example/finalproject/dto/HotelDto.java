package com.example.finalproject.dto;

import com.example.finalproject.entity.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class HotelDto {
    @Schema(hidden = true)
    private Long id;

    private String name;
    private Integer stars;
    private String address;


}
