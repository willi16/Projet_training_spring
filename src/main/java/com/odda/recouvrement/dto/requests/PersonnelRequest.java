package com.odda.recouvrement.dto.requests;

import com.odda.recouvrement.enums.RoleUtilisateur;
import jakarta.validation.constraints.*;

public record PersonnelRequest(
    @NotBlank
    String username,

    @NotBlank
    String password,

    @NotBlank
    String nom,

    @NotBlank
    String prenom,

    @Email
    String email,

    String matricule,

    @NotNull
    RoleUtilisateur role
) {}