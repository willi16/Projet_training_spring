package com.odda.recouvrement.dto.responses;

import java.util.UUID;

public record ApprenantResponse(
    UUID trackingId,
    String matricule,
    String nomComplet,
    String libelleClasse,
    String contactParent,
    String statutPaiement
) {}
