package com.example.finalproject.repository;

import com.example.finalproject.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   @Query("SELECT u FROM User u WHERE u.email = :email")
   Optional<User> findByEmail(String email);
}
