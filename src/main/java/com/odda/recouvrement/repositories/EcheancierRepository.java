package com.odda.recouvrement.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.odda.recouvrement.entities.Echeancier;

public interface EcheancierRepository extends JpaRepository<Echeancier, Long> {

    @Query ("SELECT e FROM Echeancier e WHERE e.apprenant.trackingId = :trackingId ORDER BY e.dateEcheance ASC")
    List<Echeancier> findByApprenantTrackingId(UUID trackingId);


    // Pour le Cron Job (Relance automatique)
    @Query("SELECT e FROM Echeancier e WHERE e.statut != 'PAYE' AND e.statut != 'ANNULE' AND e.dateEcheance < :aujourdhui")
    List<Echeancier> findAllEnRetard(@Param ("aujourdhui") LocalDate aujourdhui);

    @Query("SELECT COALESCE(SUM(e.montantPrevu), 0) FROM Echeancier e WHERE e.statut = 'PAYE'")
    BigDecimal sumMontantPaye();

    @Query("SELECT COALESCE(SUM(e.montantPrevu), 0) FROM Echeancier e")
    BigDecimal sumMontantTotalPrevu();

    @Query("SELECT e.apprenant.classe.libelle, COALESCE(SUM(e.montantPrevu - e.montantPaye), 0) " +
           "FROM Echeancier e WHERE e.montantPaye < e.montantPrevu " +
           "GROUP BY e.apprenant.classe.libelle")
    Map<String, BigDecimal> sumImpayesByClasse();
    
}

