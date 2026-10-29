package com.odda.recouvrement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.odda.recouvrement.entities.TypeFrais;
import java.util.UUID;


public interface TypeFraisRepository extends JpaRepository<TypeFrais, Long>{
    
    Optional<TypeFrais>  findByTrackingId(UUID trackingId);

    void deleteByTrackingId(UUID trackingId);
    
    List<TypeFrais> findFraisByClasseId(Long classeId);
    @Query ("SELECT t FROM TypeFrais t WHERE t.actif = true AND (t.classe IS NULL OR t.classe.id = :classeId )")
    List<TypeFrais> findApplicableFrais(@Param ("classeId") Long classeId);
    
} 
