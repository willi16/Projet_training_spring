package com.odda.recouvrement.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odda.recouvrement.entities.Notification;
import com.odda.recouvrement.enums.StatutNotification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByApprenantId(Long apprenantId);

    boolean existsByParent_TrackingIdAndStatutAndDateEnvoiAfter(
        UUID parentTrackingId, StatutNotification statut, LocalDateTime date
    );
    
}
