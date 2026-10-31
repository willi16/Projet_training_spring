package com.odda.recouvrement.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odda.recouvrement.entities.Personnel;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    
    Optional<Personnel> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<Personnel> findByTrackingId(UUID trackingId);
    boolean existsByTrackingId(UUID trackingId);
    
} 


