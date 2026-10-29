package com.odda.recouvrement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odda.recouvrement.entities.ImputationPaiement;

public interface ImputationPaiementRepository extends JpaRepository<ImputationPaiement, Long> {

   List<ImputationPaiement> findByPaiementIdAndEcheancierId(Long paiementId, Long Echeancier);
    
} 
