package com.odda.recouvrement.services.implementation;

import com.odda.recouvrement.dto.requests.AnneeScolaireRequest;
import com.odda.recouvrement.dto.responses.AnneeScolaireResponse;
import com.odda.recouvrement.entities.AnneeScolaire;
import com.odda.recouvrement.mappers.AnneeScolaireMapper;
import com.odda.recouvrement.repositories.AnneeScolaireRepository;
import com.odda.recouvrement.services.AnneeScolaireService; 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnneeScolaireServiceImpl implements AnneeScolaireService {
    private final AnneeScolaireRepository anneeRepository;
    private final AnneeScolaireMapper anneeMapper;

    @Transactional
    public AnneeScolaireResponse save(AnneeScolaireRequest anneeReq) {
        if (anneeRepository.existsByLibelle(anneeReq.libelle()))
            throw new IllegalArgumentException("Année scolaire déjà existante");
        return anneeMapper.toResponse(
            anneeRepository.save(
                anneeMapper.toEntity(anneeReq)
            )
        );
    }

    @Transactional(readOnly = true)
    public List<AnneeScolaireResponse> getAll() {
        return anneeRepository
            .findAll()
            .stream()
            .map(anneeMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public AnneeScolaireResponse getOne(UUID trackingId) {
        return anneeMapper
                .toResponse(anneeRepository.findByTrackingId(trackingId).orElseThrow(() -> new IllegalArgumentException("Année introuvable")));
    }

    @Transactional
    public AnneeScolaireResponse update(UUID trackingId, AnneeScolaireRequest anneeReq) {
        AnneeScolaire annee = anneeRepository.findByTrackingId(trackingId).orElseThrow(() -> new IllegalArgumentException("Année introuvable"));
        anneeMapper.updateEntity(annee);
        return anneeMapper.toResponse(anneeRepository.save(annee));
    }

    @Transactional
    public void delete(UUID trackingId) {
        anneeRepository.deleteByTrackingId(trackingId);
    } // Ou entity.setCloturee(true) pour soft delete
}
