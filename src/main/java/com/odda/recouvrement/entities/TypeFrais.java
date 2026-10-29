package com.odda.recouvrement.entities;

import com.odda.recouvrement.enums.TypeFraisCode;
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
@Table(name = "types_frais")
public class TypeFrais extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(name = "montant_reference", precision = 15, scale = 2)
    private BigDecimal montantReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_decoupage")
    private TypeFraisCode modeDecoupage;

    @Column(name = "regles_decoupage", columnDefinition = "TEXT")
    private String reglesDecoupage;

    private String cible;

    private boolean actif = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = true)
    private Classe classe;
}