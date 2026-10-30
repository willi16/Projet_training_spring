package com.odda.recouvrement.mappers;

import com.odda.recouvrement.dto.responses.EcheancierResponse;
import com.odda.recouvrement.entities.Echeancier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EcheancierMapper {

    public EcheancierResponse toResponse(Echeancier echeancier) {
        if (echeancier == null){
            return null;
        }
        
        BigDecimal reste = echeancier.getMontantPrevu().subtract(echeancier.getMontantPaye());

        return new EcheancierResponse(
            echeancier.getTrackingId(),
            echeancier.getTypeFrais() != null ? echeancier.getTypeFrais().getLibelle() : "Inconnu",
            echeancier.getMontantPrevu(),
            echeancier.getMontantPaye(),
            reste,
            echeancier.getDateEcheance(),
            echeancier.getStatut()
        );
    }
}