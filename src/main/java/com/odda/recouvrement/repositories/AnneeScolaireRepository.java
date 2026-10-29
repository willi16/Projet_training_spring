package com.odda.recouvrement.repositories;

import com.odda.recouvrement.entities.AnneeScolaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Long> {
    
    Optional<AnneeScolaire> findByLibelle(String libelle);

    boolean existsByLibelle(String libelle);

    Optional<AnneeScolaire> findByTrackingId(UUID trackingId);

    void deleteByTrackingId(UUID trackingId);

    Optional<AnneeScolaire> findTopByClotureeFalseOrderByDateRentreeDesc();
    
    List<AnneeScolaire> findAllByOrderByDateRentreeDesc();

    boolean existsByDateRentreeBeforeAndDateFinAfter(LocalDate dateRentree, LocalDate dateFin);   
}
