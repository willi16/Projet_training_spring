package com.odda.recouvrement.mappers;

import org.springframework.stereotype.Component;

import com.odda.recouvrement.dto.requests.ParentRequest;
import com.odda.recouvrement.dto.responses.ParentResponse;
import com.odda.recouvrement.entities.Parent;

@Component 
public class ParentMapper {

    public Parent toEntity(ParentRequest parentRequest){
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

    public ParentResponse toResponse(Parent parent){
        
        if (parent == null)
            return null;

        return new ParentResponse(
            parent.getTrackingId(),
            parent.getNom(),
            parent.getPrenom(),
            parent.getContact(),
            parent.getEmail(),
            parent.getAdresse(),
            parent.getProfession(),
            parent.getLienApprenant()
        );
    }

    public void updateEntity(ParentRequest parentRequest, Parent parent){
        parent.setNom(parentRequest.nom());
        parent.setPrenom(parentRequest.prenom());
        parent.setContact(parentRequest.contact());
        parent.setEmail(parentRequest.email());
        parent.setAdresse(parentRequest.adresse());
        parent.setProfession(parentRequest.profession());
        parent.setLienApprenant(parentRequest.lien());
    }
    
}
