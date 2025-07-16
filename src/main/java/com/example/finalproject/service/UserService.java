package com.example.finalproject.service;

import com.example.finalproject.config.SecurityConfig;
import com.example.finalproject.dto.UserDto;
import com.example.finalproject.entity.Reservation;
import com.example.finalproject.entity.User;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.B2CConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordUtil passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Transactional
    public User createUser(UserDto userDto) {
        User user = new User();
        List<Reservation> reservations = new ArrayList<>();
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());
        user.setReservations(reservations);
        return userRepository.save(user);
    }
    @Transactional
    public User updateUser(Long id, UserDto userDto) {
        List<Reservation> reservations = new ArrayList<>();
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());
        user.setReservations(reservations);
        return userRepository.save(user);
    }
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
         userRepository.delete(user);
    }
}