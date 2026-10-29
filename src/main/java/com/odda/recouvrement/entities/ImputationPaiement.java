package com.odda.recouvrement.entities;

import com.odda.recouvrement.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "imputations_paiement")
public class ImputationPaiement extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant_impute", precision = 15, scale = 2, nullable = false)
    private BigDecimal montantImpute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paiement_id", nullable = false)
    private Paiement paiement;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "echeancier_id", nullable = false)
    private Echeancier echeancier;
}