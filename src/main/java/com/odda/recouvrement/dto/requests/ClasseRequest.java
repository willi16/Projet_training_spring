package com.odda.recouvrement.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record ClasseRequest(
    @NotBlank(message = "Le libellé de la classe est obligatoire") String libelle,
    @Positive(message = "La capacité doit être un nombre positif") Integer capaciteTotal,
    @NotNull(message = "Le trackingId du niveau est obligatoire") UUID niveauTrackingId
) {}