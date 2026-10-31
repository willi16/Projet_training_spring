package com.odda.recouvrement.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odda.recouvrement.entities.Paiement;

import java.time.LocalDate;
import java.util.List;



public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByDatePaiement(LocalDate datePaiement);
    Optional<Paiement> findByTrackingId(UUID trackingId);
    List<Paiement> findByApprenant_TrackingIdOrderByDatePaiementDesc(UUID apprenantTrackingId);
    boolean existsByNumeroRecu(String numeroRecu);
    
}
