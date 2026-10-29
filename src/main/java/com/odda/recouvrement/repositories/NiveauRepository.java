package com.odda.recouvrement.repositories;


import com.odda.recouvrement.entities.Niveau;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NiveauRepository extends JpaRepository<Niveau, Long> {
    List<Niveau> findByAnneeScolaireId(Long anneeScolaireId);

    Optional<Niveau> findByTrackingId(UUID trackingId);

    void deleteByTrackingId(UUID trackingId);
    
    boolean existsByTrackingId(UUID trackingId);
} 
