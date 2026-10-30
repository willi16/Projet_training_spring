package com.odda.recouvrement.services;

import com.odda.recouvrement.dto.responses.EcheancierResponse;
import com.odda.recouvrement.entities.Apprenant;

import java.util.List;
import java.util.UUID;

public interface EcheancierService {
    
    // Génération d'échéancier à l'inscription d'un apprenant
    void genererPourApprenant(Apprenant apprenant);
    
    // Tableau des échéances d'un apprenant
    List<EcheancierResponse> getEcheanciersByApprenant(UUID apprenantTrackingId);
}