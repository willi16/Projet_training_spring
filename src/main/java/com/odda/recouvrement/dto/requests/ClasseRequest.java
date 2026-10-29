package com.odda.recouvrement.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClasseRequest(

    @NotBlank(message = "Le libellé de la classe est obligatoire") String libelle,
    @Positive(message = "La capacité doit être un nombre positif") Integer capaciteTotal,
    @NotNull (message = "L'ID du niveau est obligatoire") Long niveauId

) {
}
