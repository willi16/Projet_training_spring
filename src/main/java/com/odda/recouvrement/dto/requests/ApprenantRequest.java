package com.odda.recouvrement.dto.requests;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApprenantRequest(

        @NotBlank(message = "Le nom est obligatoire") 
        String nom,

        @NotBlank(message = "Le prénom est obligatoire") 
        String prenom,

        LocalDate dateNaissance,

        String sexe,

        @NotNull(message = "L'ID de la classe est obligatoire") 
        UUID classeTrackingId,

        @Valid 
        @NotNull(message = "Les informations du parent/tuteur sont obligatoires") 
        ParentRequest parent) {
}
