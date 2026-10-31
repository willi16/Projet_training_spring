package com.odda.recouvrement.services;

import com.odda.recouvrement.dto.requests.PaiementRequest;
import com.odda.recouvrement.dto.responses.PaiementResponse;
import java.util.List;
import java.util.UUID;

public interface PaiementService {
    
    /**
     * Enregistre un nouveau paiement et l'impute automatiquement aux échéances 
     * de l'apprenant selon l'algorithme FIFO (First In, First Out).
     * 
     * @param req Les données du paiement à enregistrer
     * @return La réponse contenant les détails du paiement, le montant imputé et l'éventuel reliquat
     */
    PaiementResponse enregistrerPaiement(PaiementRequest req);
    
    /**
     * Récupère l'historique de tous les paiements effectués par un apprenant donné.
     * 
     * @param apprenantTrackingId L'identifiant unique (UUID) de l'apprenant
     * @return La liste des paiements, triée du plus récent au plus ancien
     */
    List<PaiementResponse> getHistorique(UUID apprenantTrackingId);
}