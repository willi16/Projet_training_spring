package com.odda.recouvrement.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.odda.recouvrement.entities.Parent;

public interface ParentRepository extends  JpaRepository<Parent, Long>{

    Optional<Parent> findByTrackingId(UUID trackingId);
    
    Optional<Parent> findByContact(String contact);

    void deleteByTrackingId(UUID trackingId);
    
}
