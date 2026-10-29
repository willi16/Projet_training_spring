package com.odda.recouvrement.dto.responses;

import java.util.UUID;

import com.odda.recouvrement.enums.CycleScolaire;

public record NiveauResponse(

    UUID trackingId,
    String code,
    CycleScolaire libelle,
    String anneeScolaireLibelle 
) {}
