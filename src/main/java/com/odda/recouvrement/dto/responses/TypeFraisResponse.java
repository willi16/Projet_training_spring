package com.odda.recouvrement.dto.responses;

import java.math.BigDecimal;
import java.util.UUID;

import com.odda.recouvrement.enums.TypeFraisCode;

public record TypeFraisResponse(

    UUID trackingId,
    String code,
    String libelle,
    BigDecimal montantReference,
    TypeFraisCode modeDecoupage,
    String reglesDecoupage,
    String cible,
    String classeLibelle
) {}
