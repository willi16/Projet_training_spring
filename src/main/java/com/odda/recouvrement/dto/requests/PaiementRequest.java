package com.odda.recouvrement.dto.requests;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.odda.recouvrement.enums.MoyenPaiement;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaiementRequest (
    
    @NotNull (message = "Le montant est obligatoire") 
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0") 
    BigDecimal montantTotal,
    
    @NotNull(message = "Le moyen de paiement est obligatoire") MoyenPaiement moyen,
    
    String refTransaction, 
    
    @NotNull(message = "La date de paiement est obligatoire") LocalDate datePaiement,
    
    @NotNull(message = "L'apprenant est obligatoire") UUID apprenantTrackingId,
    
    UUID caissierTrackingId 


) {}
