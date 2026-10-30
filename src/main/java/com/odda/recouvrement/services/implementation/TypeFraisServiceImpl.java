package com.odda.recouvrement.services.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.odda.recouvrement.dto.requests.TypeFraisRequest;
import com.odda.recouvrement.dto.responses.TypeFraisResponse;
import com.odda.recouvrement.entities.Classe;
import com.odda.recouvrement.entities.TypeFrais;
import com.odda.recouvrement.mappers.TypeFraisMapper;
import com.odda.recouvrement.repositories.ClasseRepository;
import com.odda.recouvrement.repositories.TypeFraisRepository;
import com.odda.recouvrement.services.TypeFraisService;

import lombok.RequiredArgsConstructor;


@Service 
@RequiredArgsConstructor 
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

    @Override 
    @Transactional 
    public List<TypeFraisResponse> getAll(){

        return typeRepository.findAll().stream()
                .map(typeMapper::toResponse)
                .toList();
    }


    @Override 
    @Transactional(readOnly = true)
    public TypeFraisResponse getOne(UUID trackingId){

        TypeFrais type = typeRepository.findByTrackingId(trackingId)
            .orElseThrow(() -> new IllegalArgumentException("Type de frais introuvable"));
        return typeMapper.toResponse(type);
    }
    
    @Override 
    @Transactional
    public TypeFraisResponse update(UUID trackingId , TypeFraisRequest typeReq){

        TypeFrais existing = typeRepository.findByTrackingId(trackingId)
            .orElseThrow(() -> new IllegalArgumentException("Type de frais introuvable"));

        Classe classe = typeReq.classeId() != null ? classeRepository.findById(typeReq.classeId())
            .orElseThrow(() -> new IllegalArgumentException("Classe introuvable")) : null;

        typeMapper.updateEntity(typeReq, existing, classe);

        return typeMapper.toResponse(typeRepository.save(existing));
    }

    @Override
    @Transactional
    public void delete(UUID trackingId) {
        typeRepository.deleteByTrackingId(trackingId);
    }

}
