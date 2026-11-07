package com.odda.recouvrement.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.odda.recouvrement.entities.Paiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByDatePaiement(LocalDate datePaiement);

    Optional<Paiement> findByTrackingId(UUID trackingId);

    List<Paiement> findByApprenant_TrackingIdOrderByDatePaiementDesc(UUID apprenantTrackingId);

    boolean existsByNumeroRecu(String numeroRecu);

    @Query("SELECT SUM(p.montantTotal) FROM Paiement p WHERE p.datePaiement = :date")
    BigDecimal sumMontantByDate(@Param ("date") LocalDate date);

    @Query("SELECT p.caissier.matricule, SUM(p.montantTotal) FROM Paiement p " +
            "WHERE p.caissier.trackingId = :id AND p.datePaiement BETWEEN :debut AND :fin GROUP BY p.caissier.matricule")

    Map<String, BigDecimal> sumMontantByCaissier(@Param("id") UUID id, @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);



}
