package com.odda.recouvrement.entities;

import com.odda.recouvrement.utils.BaseEntity;
import com.odda.recouvrement.enums.StatutEcheance;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "echeanciers")
public class Echeancier extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant_prevu", precision = 15, scale = 2, nullable = false)
    private BigDecimal montantPrevu;

    @Column(name = "montant_paye", precision = 15, scale = 2)
    private BigDecimal montantPaye = BigDecimal.ZERO;

    @Column(name = "date_echeance", nullable = false)
    private LocalDate dateEcheance;

    @Enumerated(EnumType.STRING)
    private StatutEcheance statut = StatutEcheance.A_VENIR;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apprenant_id", nullable = false)
    private Apprenant apprenant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_frais_id", nullable = false)
    private TypeFrais typeFrais;
}