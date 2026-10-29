package com.odda.recouvrement.utils;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseEntity {

    @Column(name = "date_creation", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate createDate;

    @Column(name = "date_modification")
    @LastModifiedDate
    private LocalDate updateDate;

    @CreatedBy
    private String userCreate;

    @LastModifiedBy
    private String userUpdate;

    @Version // Pour avoir les version des modifications de chaque ligne
    private int version;

    @Column(unique = true, nullable = false)
    private UUID trackingId;

    @PrePersist
    public void prePersist() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }

}
