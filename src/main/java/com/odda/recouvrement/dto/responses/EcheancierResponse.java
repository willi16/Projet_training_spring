package com.odda.recouvrement.dto.responses;

import com.odda.recouvrement.enums.StatutEcheance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EcheancierResponse(
    UUID trackingId,
    String libelleTypeFrais, 
    BigDecimal montantPrevu,
    BigDecimal montantPaye,
    BigDecimal resteAPayer,  
    LocalDate dateEcheance,
    StatutEcheance statut
) {}