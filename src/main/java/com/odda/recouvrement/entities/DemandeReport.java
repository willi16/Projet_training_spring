package com.odda.recouvrement.entities;

import com.odda.recouvrement.utils.BaseEntity;
import com.odda.recouvrement.enums.StatutDemandeDerogation;
import com.odda.recouvrement.enums.TypeDemandeDerogation;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "demandes_report")
public class DemandeReport extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeDemandeDerogation type;

    private String motif;

    @Column(name = "nouvelle_date")
    private LocalDate nouvelleDate;

    @Enumerated(EnumType.STRING)
    private StatutDemandeDerogation statut = StatutDemandeDerogation.EN_ATTENTE;

    @Column(name = "date_validation")
    private LocalDate dateValidation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apprenant_id", nullable = false)
    private Apprenant apprenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandeur_id")
    private Personnel demandeur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validateur_id")
    private Personnel validateur;
}