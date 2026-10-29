package com.odda.recouvrement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odda.recouvrement.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByApprenantId(Long apprenantId);
    
}
