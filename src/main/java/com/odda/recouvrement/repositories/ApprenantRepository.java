package com.odda.recouvrement.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.odda.recouvrement.entities.Apprenant;

public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {

    Optional<Apprenant> findByMatricule(String matricule);
    boolean existsByMatricule(String matricule);

    List<Apprenant> findAllByActifTrue();

    boolean existsByParentId(Long parentId);

    Optional<Apprenant> findByTrackingIdAndActifTrue(UUID trackingId);
  
    List<Apprenant> findByClasseIdAndActifTrue(Long classeId);
}