package com.example.finalproject.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class SearchDto {
    private Double minPrice;
    private Double maxPrice;
    private Integer minCapacity;
    private Integer maxCapacity;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long hotelId;
}