package com.odda.recouvrement.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RetardResponse(
    UUID echeancierTrackingId,
    UUID apprenantTrackingId,
    String matriculeApprenant,
    String nomApprenant,
    String classeLibelle,
    String libelleTypeFrais,
    LocalDate dateEcheance,
    BigDecimal montantPrevu,
    BigDecimal montantPaye,
    BigDecimal resteAPayer,
    Long joursDeRetard,           // ⬅️ Calculé à la volée
    String statut,
    String parentNom,
    String parentContact
) {}