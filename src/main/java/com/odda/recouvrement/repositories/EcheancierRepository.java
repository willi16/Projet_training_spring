package com.odda.recouvrement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odda.recouvrement.entities.Echeancier;

public interface EcheancierRepository extends JpaRepository<Echeancier, Long> {

    List<Echeancier> findByApprenantId(Long apprenantId);
    
}
