package com.odda.recouvrement.entities;

import com.odda.recouvrement.enums.RoleUtilisateur;
import com.odda.recouvrement.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "personnels")
public class Personnel extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username; 

    @Column(nullable = false)
    private String password;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true, length = 20)
    private String matricule; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUtilisateur role;
    
    @Builder.Default
    private boolean actif = true;
}