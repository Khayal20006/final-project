package com.example.finalproject.dto;

import com.example.finalproject.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private Long id;

    private String fullName;

    private String email;

    private String password;

    private Role role;

    private Long reservationId;

}

