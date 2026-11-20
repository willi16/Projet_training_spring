package com.odda.recouvrement.dto.responses;

import java.time.LocalDate;
import java.util.UUID;

public record ApprenantResponse(
    UUID trackingId,
    String matricule,
    String nom,
    String prenom,
    LocalDate dateNaissance,
    String sexe,
    UUID classeTrackingId,
    String classeLibelle,
    ParentResponse parent,       
    String statutPaiement
) {}