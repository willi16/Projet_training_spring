package com.odda.recouvrement.entities;

import com.odda.recouvrement.utils.BaseEntity;
import java.io.Serializable;
import com.odda.recouvrement.enums.LienParental;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "parents")
public class Parent extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String contact;

    private String email;

    private String adresse;

    private String profession;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "lien_apprenant")
    private LienParental lienApprenant;
}