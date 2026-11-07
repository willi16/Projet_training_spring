package com.odda.recouvrement.dto.responses;

import com.odda.recouvrement.enums.RoleUtilisateur;
import java.util.UUID;

public record PersonnelResponse(
    UUID trackingId,
    String username,
    String nom,
    String prenom,
    String email,
    String matricule,
    RoleUtilisateur role,
    boolean actif
) {}