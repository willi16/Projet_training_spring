package com.odda.recouvrement.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.odda.recouvrement.entities.Echeancier;

public interface EcheancierRepository extends JpaRepository<Echeancier, Long> {

    @Query ("SELECT e FROM Echeancier e WHERE e.apprenant.trackingId = :trackingId ORDER BY e.dateEcheance ASC")
    List<Echeancier> findByApprenantTrackingId(UUID trackingId);
    
}

//  // Pour le Cron Job (Relance automatique)
//     @Query("SELECT e FROM Echeancier e WHERE e.statut != 'PAYE' AND e.statut != 'ANNULE' AND e.dateEcheance < :aujourdhui")
//     List<Echeancier> findAllEnRetard(@Param("aujourdhui") LocalDate aujourdhui);
