package com.odda.recouvrement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odda.recouvrement.entities.Paiement;

import java.time.LocalDate;
import java.util.List;



public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    Optional<Paiement> findByNumeroRecu(String numeroRecu);
    List<Paiement> findByDatePaiement(LocalDate datePaiement);
    
}
