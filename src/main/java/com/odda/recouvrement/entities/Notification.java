package com.odda.recouvrement.entities;

import com.odda.recouvrement.utils.BaseEntity;
import com.odda.recouvrement.enums.CanalNotification;
import com.odda.recouvrement.enums.StatutNotification;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CanalNotification canal;

    private String contenu;

    @Enumerated(EnumType.STRING)
    private StatutNotification statut = StatutNotification.EN_ATTENTE;

    @Column(name = "date_envoi")
    private LocalDateTime dateEnvoi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apprenant_id")
    private Apprenant apprenant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;
}