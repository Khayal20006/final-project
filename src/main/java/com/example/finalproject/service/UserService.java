package com.example.finalproject.service;

import com.example.finalproject.config.PasswordConfig;
import com.example.finalproject.config.SwaggerConfig;
import com.example.finalproject.dto.UserDto;
import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.User;
import com.example.finalproject.entity.enums.Role;
import com.example.finalproject.exception.UserNotFoundException;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    @Transactional
    public User createUser(UserDto userDto) {
        User user = new User();
        List<Reservation> reservations = new ArrayList<>();
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());
        user.setReservations(reservations);
        return userRepository.save(user);
    }
    @Transactional
    public User updateUser(Long id, UserDto userDto) {
        List<Reservation> reservations = new ArrayList<>();
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());
        user.setReservations(reservations);
        return userRepository.save(user);
    }
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
         userRepository.delete(user);
    }
}