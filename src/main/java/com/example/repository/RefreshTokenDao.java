package com.example.repository;

import com.example.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenDao extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByValue(String value);
}