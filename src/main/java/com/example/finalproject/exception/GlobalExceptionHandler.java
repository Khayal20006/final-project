package com.example.finalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleHotelNotFound(HotelNotFoundException e, WebRequest request) {
        return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleReservationNotFound(ReservationNotFoundException e, WebRequest request) {
        return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRoomNotFound(RoomNotFoundException e, WebRequest request) {
        return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<Map<String, Object>> handleRoomNotAvailable(RoomNotAvailableException e, WebRequest request) {
        return createErrorResponse(e.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException e, WebRequest request) {
        return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Map<String, Object>> handleWrongPassword(WrongPasswordException e, WebRequest request) {
        return createErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherError(Exception e, WebRequest request) {
        e.printStackTrace();
        System.out.println("=== EXCEPTION DEBUG ===");
        System.out.println("Exception type: " + e.getClass().getName());
        System.out.println("Exception message: " + e.getMessage());
        System.out.println("Stack trace:");
        e.printStackTrace();
        System.out.println("========================");

        return createErrorResponse("Sistem xətası: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
    private ResponseEntity<Map<String, Object>> createErrorResponse(String message, HttpStatus status, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status.value());

        return new ResponseEntity<>(response, status);
    }
}