package com.odda.recouvrement.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.odda.recouvrement.dto.requests.ParentRequest;
import com.odda.recouvrement.dto.responses.ParentResponse;
import com.odda.recouvrement.entities.Parent;
import com.odda.recouvrement.mappers.ParentMapper;
import com.odda.recouvrement.repositories.ApprenantRepository;
import com.odda.recouvrement.repositories.ParentRepository;
import com.odda.recouvrement.services.ParentService;


import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ParentServiceImpl implements ParentService {
    
    private final ParentRepository parentRepository;

    private final ParentMapper parentMapper;

    private final ApprenantRepository apprenantRepository;

    @Override 
    @Transactional 
    public ParentResponse save(ParentRequest parentReq) {
        if (parentRepository.findByContact(parentReq.contact()).isPresent()) {
            throw new IllegalArgumentException("Un parent avec ce numéro de contact existe déjà.");
        }
        Parent saved = parentRepository.save(parentMapper.toEntity(parentReq));
        return parentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentResponse> getAll() {
        return parentRepository.findAll().stream()
                .map(parentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParentResponse getOne(UUID trackingId) {
        Parent parent = parentRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Parent introuvable avec l'ID : " + trackingId));
        return parentMapper.toResponse(parent);
    }


    @Override
    @Transactional
    public ParentResponse update(UUID trackingId, ParentRequest parentReq) {
        // 1. Récupérer le parent existant
        Parent existing = parentRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Parent introuvable avec l'ID : " + trackingId));
        
        // On cherche si ce nouveau numéro existe déjà en base
        Optional<Parent> parentAvecNouveauContact = parentRepository.findByContact(parentReq.contact());
        
        // Si on trouve un parent avec ce numéro, ET que ce n'est PAS le parent qu'on est en train de modifier
        if (parentAvecNouveauContact.isPresent() && !parentAvecNouveauContact.get().getTrackingId().equals(trackingId)) {
            throw new IllegalArgumentException(
                "Ce numéro de contact (" + parentReq.contact() + ") est déjà utilisé par un autre parent."
            );
        }

        parentMapper.updateEntity(parentReq, existing);

        
        Parent updated = parentRepository.save(existing);
        return parentMapper.toResponse(updated);
    }

    // @Override
    // @Transactional
    // public void delete(UUID trackingId) {
    //     parentRepository.deleteByTrackingId(trackingId);
    // }

    @Override
    @Transactional
    public void delete(UUID trackingId) {
        // Récupérer un parent pour vérification
        Parent parent = parentRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Parent introuvable avec le trackingId : " + trackingId));

        boolean aDesEnfants = apprenantRepository.existsByParentId(parent.getId());
        
        if (aDesEnfants) {
            throw new IllegalStateException(
                "Suppression interdite : Ce parent est lié à un ou plusieurs apprenants. " +
                "Veuillez d'abord réaffecter ou supprimer les apprenants associés."
            );
        }

        parentRepository.delete(parent); 
    }

}
