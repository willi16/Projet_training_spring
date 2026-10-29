package com.odda.recouvrement.dto.responses;

import java.util.UUID;

public record ClasseResponse(

    UUID trackingId,
    String libelle,
    Integer capaciteTotal,
    Integer effectif,
    String niveauLibelle 
) {}
