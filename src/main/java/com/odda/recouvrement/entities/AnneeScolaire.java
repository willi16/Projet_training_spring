package com.odda.recouvrement.entities;

import com.odda.recouvrement.utils.BaseEntity;
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
@Table(name = "annees_scolaires")
public class AnneeScolaire extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String libelle;

    @Column(nullable = false)
    private LocalDate dateRentree;

    @Column(nullable = false)
    private LocalDate dateFin;
    
    @Column(nullable = false)
    private boolean cloturee = false;
}