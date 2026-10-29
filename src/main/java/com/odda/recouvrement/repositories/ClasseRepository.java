package com.odda.recouvrement.repositories;

import com.odda.recouvrement.entities.Classe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClasseRepository extends JpaRepository<Classe, Long> {

    List<Classe> findByNiveauId(Long niveauId);

    Optional<Classe> findByTrackingId(UUID trackingId);

    void deleteByTrackingId(UUID trackingId);

    boolean existsByTrackingId(UUID trackingId);

    boolean existsByLibelleAndNiveauId(String libelle, Long niveauId);
    
} 
