package com.odda.recouvrement.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.odda.recouvrement.enums.MoyenPaiement;
import com.odda.recouvrement.enums.StatutPaiement;

public record PaiementResponse(

    UUID trackingId,
    String numeroRecu,
    BigDecimal montantTotal,
    MoyenPaiement moyen,
    LocalDate datePaiement,
    StatutPaiement statut,
    String matriculeApprenant,
    BigDecimal totalImpute,    
    BigDecimal reliquat 

) {}
