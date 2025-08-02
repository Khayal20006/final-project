package com.example.finalproject.service;

import com.example.finalproject.dto.LoginRequestDto;
import com.example.finalproject.dto.LoginResponseDto;
import com.example.finalproject.dto.UserDto;
import com.example.finalproject.entity.User;
import com.example.finalproject.entity.enums.Role;
import com.example.finalproject.exception.UserNotFoundException;
import com.example.finalproject.exception.WrongPasswordException;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }

        String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getRole().name());

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());

        return new LoginResponseDto(accessToken, refreshToken, "Login successful", userDto);
    }

    @Transactional
    public LoginResponseDto register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getRole().name());

        userDto.setId(user.getId());
        userDto.setPassword(null);

        return new LoginResponseDto(accessToken, refreshToken, "Registration successful", userDto);
    }
}
