package com.odda.recouvrement.dto.requests;

import com.odda.recouvrement.enums.CycleScolaire;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NiveauRequest(

    @NotBlank(message = "Le code du niveau est obligatoire") String code,
    @NotNull(message = "Le cycle scolaire est obligatoire") CycleScolaire libelle,
    @NotNull(message = "L'ID de l'année scolaire est obligatoire") Long anneeScolaireId

) {}
