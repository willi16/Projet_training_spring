package com.odda.recouvrement.entities;

import com.odda.recouvrement.utils.BaseEntity;
import com.odda.recouvrement.enums.MoyenPaiement;
import com.odda.recouvrement.enums.StatutPaiement;
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
@Table(name = "paiements")
public class Paiement extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_recu", unique = true, nullable = false)
    private String numeroRecu;

    @Column(name = "montant_total", precision = 15, scale = 2, nullable = false)
    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    private MoyenPaiement moyen;

    @Column(name = "ref_transaction")
    private String refTransaction;

    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statut = StatutPaiement.VALIDE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apprenant_id", nullable = false)
    private Apprenant apprenant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caissier_id")
    private Personnel caissier;
}