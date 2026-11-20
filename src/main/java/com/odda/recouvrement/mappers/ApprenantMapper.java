package com.odda.recouvrement.mappers;

import org.springframework.stereotype.Component;
import com.odda.recouvrement.dto.requests.ApprenantRequest;
import com.odda.recouvrement.dto.requests.ParentRequest;
import com.odda.recouvrement.dto.responses.ApprenantResponse;
import com.odda.recouvrement.dto.responses.ParentResponse;
import com.odda.recouvrement.entities.Apprenant;
import com.odda.recouvrement.entities.Parent;
import com.odda.recouvrement.entities.Classe;

@Component
public class ApprenantMapper {

    public Parent toParentEntity(ParentRequest parentRequest) {
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

    public Apprenant toApprenantEntity(ApprenantRequest apprenantRequest, Parent parent, Classe classe) {
        if (apprenantRequest == null)
            return null;
        return Apprenant.builder()
                .nom(apprenantRequest.nom())
                .prenom(apprenantRequest.prenom())
                .dateNaissance(apprenantRequest.dateNaissance())
                .sexe(apprenantRequest.sexe())
                .parent(parent)
                .classe(classe)
                .build();
    }

    // NOUVELLE MÉTHODE D'UPDATE (Comme votre exemple Classe)
    public void updateEntity(ApprenantRequest request, Apprenant existing, Parent parent, Classe classe) {
        if (request == null || existing == null)
            return;

        existing.setNom(request.nom());
        existing.setPrenom(request.prenom());
        existing.setDateNaissance(request.dateNaissance());
        existing.setSexe(request.sexe());

        // On met à jour les relations
        existing.setParent(parent);
        existing.setClasse(classe);

    }

    public ApprenantResponse toResponse(Apprenant apprenant) {
        if (apprenant == null)
            return null;

        ParentResponse parentResponse = null;
        if (apprenant.getParent() != null) {
            Parent p = apprenant.getParent();
            parentResponse = new ParentResponse(
                    p.getTrackingId(),
                    p.getNom(),
                    p.getPrenom(),
                    p.getContact(),
                    p.getEmail(),
                    p.getAdresse(),
                    p.getProfession(),
                    p.getLienApprenant() 
            );
        }

        return new ApprenantResponse(
                apprenant.getTrackingId(),
                apprenant.getMatricule(),
                apprenant.getNom(),
                apprenant.getPrenom(),
                apprenant.getDateNaissance(),
                apprenant.getSexe(),
                apprenant.getClasse() != null ? apprenant.getClasse().getTrackingId() : null,
                apprenant.getClasse() != null ? apprenant.getClasse().getLibelle() : null,
                parentResponse,
                apprenant.getStatutPaiement() != null ? apprenant.getStatutPaiement().name() : null);
    }
}