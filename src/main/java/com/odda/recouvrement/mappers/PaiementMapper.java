package com.odda.recouvrement.mappers;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.odda.recouvrement.dto.requests.PaiementRequest;
import com.odda.recouvrement.dto.responses.PaiementResponse;
import com.odda.recouvrement.entities.Apprenant;
import com.odda.recouvrement.entities.Paiement;
import com.odda.recouvrement.entities.Personnel;

@Component 
public class PaiementMapper {
    
    public Paiement toEntity(PaiementRequest paiementReq, Apprenant apprenant, Personnel caissier){

        if (paiementReq == null || apprenant == null){
            return null;
        }

        return Paiement.builder()
                .numeroRecu(paiementReq.numeroRecu())
                .montantTotal(paiementReq.montantTotal())
                .moyen(paiementReq.moyen())
                .refTransaction(paiementReq.refTransaction())
                .datePaiement(paiementReq.datePaiement())
                .apprenant(apprenant)
                .caissier(null)
                .build();

    }

    public PaiementResponse toResponse(Paiement paiement, BigDecimal totalImpute, BigDecimal reliquat){

        if (paiement == null){
            return  null;
        }

        return new PaiementResponse(
            paiement.getTrackingId(),
            paiement.getNumeroRecu(),
            paiement.getMontantTotal(),
            paiement.getMoyen(),
            paiement.getDatePaiement(),
            paiement.getStatut(),
            paiement.getApprenant() != null ? paiement.getApprenant().getMatricule() : "Inconnu",
            totalImpute,
            reliquat
        );
    }
}
