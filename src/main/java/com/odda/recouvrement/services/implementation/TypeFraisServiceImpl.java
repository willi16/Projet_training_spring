package com.odda.recouvrement.services.implementation;

import org.springframework.transaction.annotation.Transactional;

import com.odda.recouvrement.dto.requests.TypeFraisRequest;
import com.odda.recouvrement.dto.responses.TypeFraisResponse;
import com.odda.recouvrement.entities.Classe;
import com.odda.recouvrement.entities.TypeFrais;
import com.odda.recouvrement.mappers.TypeFraisMapper;
import com.odda.recouvrement.repositories.ClasseRepository;
import com.odda.recouvrement.repositories.TypeFraisRepository;
import com.odda.recouvrement.services.TypeFraisService;

public class TypeFraisServiceImpl implements TypeFraisService {

    private final TypeFraisRepository typeRepository;

    private final ClasseRepository classeRepository;

    private final TypeFraisMapper typeMapper;

    @Override 
    @Transactional 
    public TypeFraisResponse save(TypeFraisRequest typeReq) {
        
        if (typeRepository.existsByCode(typeReq.code())) {
            throw new IllegalArgumentException("Un type de frais avec le code [" + typeReq.code() + "] existe déjà.");
        }

        Classe classe = typeReq.classeId() != null 
            ? classeRepository.findById(typeReq.classeId())
                .orElseThrow(() -> new IllegalArgumentException("Classe introuvable avec l'ID : " + typeReq.classeId()))
            : null;

        TypeFrais saved = typeRepository.save(typeMapper.toEntity(typeReq, classe));
        return typeMapper.toResponse(saved);
    } 
    
}
