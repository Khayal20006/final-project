package com.example.finalproject.entity;
import com.example.finalproject.entity.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomNumber;

    private Integer capacity;

    private Double price;

    private Boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    private Hotel hotel;

    @OneToOne(mappedBy = "room")
    @JsonIgnore
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    private RoomType type;
}

