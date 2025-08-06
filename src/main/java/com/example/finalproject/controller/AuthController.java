package com.example.finalproject.controller;

import com.example.finalproject.dto.LoginRequestDto;
import com.example.finalproject.dto.LoginResponseDto;
import com.example.finalproject.dto.UserDto;
import com.example.finalproject.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequest) {
            LoginResponseDto response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "User registration")
    public ResponseEntity<LoginResponseDto> register(@RequestBody @Valid UserDto userDto) {
            LoginResponseDto response = authService.register(userDto);
            return ResponseEntity.ok(response);

    }
}