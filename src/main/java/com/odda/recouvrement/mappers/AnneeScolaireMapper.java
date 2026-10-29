package com.odda.recouvrement.mappers;

import org.springframework.stereotype.Component;

import com.odda.recouvrement.dto.requests.AnneeScolaireRequest;
import com.odda.recouvrement.dto.responses.AnneeScolaireResponse;
import com.odda.recouvrement.entities.AnneeScolaire;

@Component 
public class AnneeScolaireMapper {
    
    public AnneeScolaire toEntity(AnneeScolaireRequest anneeRequest){
        return AnneeScolaire.builder()
        .libelle(anneeRequest.libelle())
        .dateRentree(anneeRequest.dateRentree())
        .dateFin(anneeRequest.dateFin())
        .build();
    }

    public AnneeScolaireResponse toResponse(AnneeScolaire annee){
        return new AnneeScolaireResponse(
            annee.getTrackingId(),
            annee.getLibelle(),
            annee.getDateRentree(),annee.getDateFin(),
            annee.isCloturee()

        );
    } 

    public void updateEntity(AnneeScolaire annee){

        annee.setLibelle(annee.getLibelle());
        annee.setDateRentree(annee.getDateRentree());
        annee.setDateFin(annee.getDateFin());

    }
}
