package com.odda.recouvrement.mappers;


import org.springframework.stereotype.Component;

import com.odda.recouvrement.dto.requests.ApprenantRequest;
import com.odda.recouvrement.dto.requests.ParentRequest;
import com.odda.recouvrement.dto.responses.ApprenantResponse;
import com.odda.recouvrement.entities.Apprenant;
import com.odda.recouvrement.entities.Parent;
import com.odda.recouvrement.entities.Classe;



@Component 
public class ApprenantMapper {
    
    public Parent toParentEntity(ParentRequest parentRequest){

        if (parentRequest == null)
            return null;

        return Parent.builder()
            .nom(parentRequest.nom())
            .prenom(parentRequest.prenom())
            .contact(parentRequest.contact())
            .email(parentRequest.email())
            .adresse(parentRequest.adresse())
            .profession(parentRequest.profession())
            .lienApprenant(parentRequest.lien())
            .build();
    }


    public Apprenant toApprenantEntity(ApprenantRequest apprenantRequest, Parent parent, Classe classe){

        if (apprenantRequest == null)
            return null;

        return Apprenant.builder()
            .matricule(apprenantRequest.matricule())
            .nom(apprenantRequest.nom())
            .prenom(apprenantRequest.prenom())
            .dateNaissance(apprenantRequest.dateNaissance())
            .sexe(apprenantRequest.sexe())
            .parent(parent)
            .classe(classe)
            .build();
    } 


    public ApprenantResponse toResponse(Apprenant apprenant) {

        if (apprenant == null)
            return null;

        return new ApprenantResponse(
                apprenant.getTrackingId(),
                apprenant.getMatricule(),
                apprenant.getNom() + " " + apprenant.getPrenom(),
                apprenant.getClasse() != null ? apprenant.getClasse().getLibelle() : null,
                apprenant.getParent() != null ? apprenant.getParent().getContact() : null,
                apprenant.getStatutPaiement() != null ? apprenant.getStatutPaiement().name() : null
        );
    }



    public Apprenant toEntityUpdate(ApprenantRequest apprenantRequest, Apprenant existing) {

        if (apprenantRequest == null || existing == null) {
            return existing;
        }

        existing.setNom(apprenantRequest.nom());
        existing.setPrenom(apprenantRequest.prenom());
        existing.setDateNaissance(apprenantRequest.dateNaissance());
        existing.setSexe(apprenantRequest.sexe());
        
        
        return existing;
    }
    
}
