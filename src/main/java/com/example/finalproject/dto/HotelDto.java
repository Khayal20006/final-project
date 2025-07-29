package com.example.finalproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class HotelDto {
    @Schema(hidden = true)
    private Long id;

    @NotBlank
    private String name;

    private Integer stars;

    @NotBlank
    private String address;
}
