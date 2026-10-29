package com.odda.recouvrement.mappers;

import org.springframework.stereotype.Component;

import com.odda.recouvrement.dto.requests.NiveauRequest;
import com.odda.recouvrement.dto.responses.NiveauResponse;
import com.odda.recouvrement.entities.AnneeScolaire;
import com.odda.recouvrement.entities.Niveau;

@Component 
public class NiveauMapper {

    public Niveau toEntity(NiveauRequest niveauRequest , AnneeScolaire annee){
        return Niveau.builder()
            .code(niveauRequest.code())
            .libelle(niveauRequest.libelle())
            .anneeScolaire(annee)
            .build();
    }

    public NiveauResponse toResponse(Niveau niveau){
        return new NiveauResponse(
            niveau.getTrackingId(),
            niveau.getCode(),
            niveau.getLibelle(),
            niveau.getAnneeScolaire() != null ? niveau.getAnneeScolaire().getLibelle() : null
        );
    }

    public void updateEntity(NiveauRequest niveauRequest, Niveau niveau, AnneeScolaire annee){
        niveau.setCode(niveauRequest.code());
        niveau.setLibelle(niveauRequest.libelle());
        niveau.setAnneeScolaire(annee);
    }
    
}
