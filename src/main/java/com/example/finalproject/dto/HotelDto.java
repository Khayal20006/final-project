package com.example.finalproject.dto;

import com.example.finalproject.entity.Room;
import lombok.Data;

import java.util.List;

@Data
public class HotelDto {

    private Long id;

    private String name;
    private Integer stars;
    private String address;


}
