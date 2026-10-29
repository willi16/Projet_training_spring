package com.odda.recouvrement.mappers;

import java.util.UUID;

import com.odda.recouvrement.dto.requests.TypeFraisRequest;
import com.odda.recouvrement.dto.responses.TypeFraisResponse;
import com.odda.recouvrement.entities.Classe;
import com.odda.recouvrement.entities.TypeFrais;

public class TypeFraisMapper {

    public TypeFrais toEntity(TypeFraisRequest typeReq , Classe classe){
        if (typeReq == null){
            return null;
        }
        return TypeFrais.builder()
        .code(typeReq.code())
        .libelle(typeReq.libelle())
        .montantReference(typeReq.montantReference())
        .modeDecoupage(typeReq.modeDecoupage())
        .reglesDecoupage(typeReq.reglesDecoupage())
        .cible(typeReq.cible())
        .classe(classe)
        .actif(true)
        .build();
    }

    public TypeFraisResponse toResponse(TypeFrais type){
        if (type == null){
            return null;
        }
        return new TypeFraisResponse(
            type.getTrackingId(),
            type.getCode(),
            type.getLibelle(),
            type.getMontantReference(),
            type.getModeDecoupage(),
            type.getReglesDecoupage(),
            type.getCible(),
            type.getClasse() != null ? type.getClasse().getLibelle() : "Tous niveaux"
        );
    }

    public void updateEntity(TypeFraisRequest typeReq, TypeFrais type, Classe classe){
        if (typeReq == null || type == null){
            return;
        }

        type.setCode(typeReq.code());
        type.setLibelle(typeReq.libelle());
        type.setMontantReference(typeReq.montantReference());
        type.setModeDecoupage(typeReq.modeDecoupage());
        type.setReglesDecoupage(typeReq.reglesDecoupage());
        type.setCible(typeReq.cible());
        type.setClasse(classe);

    }
    
}
