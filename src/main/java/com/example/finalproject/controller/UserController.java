package com.example.finalproject.controller;

import com.example.finalproject.dto.UserDto;
import com.example.finalproject.entity.User;
import com.example.finalproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
@Tag(description = "Userle bagli butun esas emeliyyetlar admin ucun",name = "User emeliyyatlari")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Bazada olan butun userleri getirir admin ucun")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Userleri adlarla tapir admin ucun")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @Operation(summary = "Userleri  yaradir  admin ucun")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDto userDto) {
        User createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }
    @PutMapping("/{id}")
    @Operation(summary = "ID ye gore useri update edir  admin ucun")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "ID ye gore useri delete edir  admin ucun")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
