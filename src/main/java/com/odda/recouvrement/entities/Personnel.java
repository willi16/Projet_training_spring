package com.odda.recouvrement.entities;

import com.odda.recouvrement.utils.BaseEntity;
import java.io.Serializable;
import com.odda.recouvrement.enums.RoleUtilisateur;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "utilisateurs")
public class Personnel extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    private RoleUtilisateur role;
    
    private boolean actif = true;
}