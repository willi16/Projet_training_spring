package com.odda.recouvrement.mappers;

import org.springframework.stereotype.Component;

import com.odda.recouvrement.dto.requests.ClasseRequest;
import com.odda.recouvrement.dto.responses.ClasseResponse;
import com.odda.recouvrement.entities.Classe;
import com.odda.recouvrement.entities.Niveau;

@Component 
public class ClasseMapper {

    public Classe toEntity(ClasseRequest classeRequest, Niveau niveau){
        return Classe.builder()
            .libelle(classeRequest.libelle())
            .capaciteTotal(classeRequest.capaciteTotal())
            .effectif(0)
            .niveau(niveau)
            .build();
    }

    public ClasseResponse toResponse(Classe classe){
        return new ClasseResponse(
            classe.getTrackingId(),
            classe.getLibelle(),
            classe.getCapaciteTotal(),
            classe.getEffectif(),
            classe.getNiveau() != null ? classe.getNiveau().getLibelle().name() : null
        );
    }

    public void updateEntity(ClasseRequest classeRequest, Classe classe, Niveau niveau){
        classe.setLibelle(classeRequest.libelle());
        classe.setCapaciteTotal(classeRequest.capaciteTotal());
        classe.setNiveau(niveau);
    }
    
}
