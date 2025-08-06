package com.example.finalproject.dto;

import com.example.finalproject.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "Tam ad bos ola bilmez")
    private String fullName;

    @Email(message = "Email düzgün formatda olmalıdır")
    @NotBlank(message = "Email boş ola bilməz")
    private String email;

    @Size(min = 6,message = "Min 6 simvoldan iaret olmalidir")
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long reservationId;

}

