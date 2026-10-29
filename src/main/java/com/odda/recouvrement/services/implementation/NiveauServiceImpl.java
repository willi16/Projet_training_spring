package com.odda.recouvrement.services.implementation;

import com.odda.recouvrement.dto.requests.NiveauRequest;
import com.odda.recouvrement.dto.responses.NiveauResponse;
import com.odda.recouvrement.entities.AnneeScolaire;
import com.odda.recouvrement.entities.Niveau;
import com.odda.recouvrement.mappers.NiveauMapper;
import com.odda.recouvrement.repositories.AnneeScolaireRepository;
import com.odda.recouvrement.repositories.NiveauRepository;
import com.odda.recouvrement.services.NiveauService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NiveauServiceImpl implements NiveauService {

    private final NiveauRepository niveauRepository;
    private final AnneeScolaireRepository anneeRepository;
    private final NiveauMapper niveauMapper;

    @Override
    @Transactional
    public NiveauResponse save(NiveauRequest niveauReq) {
        AnneeScolaire annee = anneeRepository.findById(niveauReq.anneeScolaireId())
                .orElseThrow(() -> new IllegalArgumentException("Année scolaire introuvable avec l'ID : " + niveauReq.anneeScolaireId()));
        
        Niveau saved = niveauRepository.save(niveauMapper.toEntity(niveauReq, annee));
        return niveauMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NiveauResponse> getAll() {
        return niveauRepository.findAll().stream().map(niveauMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public NiveauResponse getOne(UUID trackingId) {
        Niveau niveau = niveauRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Niveau introuvable avec l'ID : " + trackingId));
        return niveauMapper.toResponse(niveau);
    }

    @Override
    @Transactional
    public NiveauResponse update(UUID trackingId, NiveauRequest niveauReq) {
        Niveau niveau = niveauRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Niveau introuvable avec l'ID : " + trackingId));
        
        AnneeScolaire annee = anneeRepository.findById(niveauReq.anneeScolaireId())
                .orElseThrow(() -> new IllegalArgumentException("Année scolaire introuvable"));

        niveauMapper.updateEntity(niveauReq, niveau, annee);
        return niveauMapper.toResponse(niveauRepository.save(niveau));
    }

    @Override
    @Transactional
    public void delete(UUID trackingId) {
        niveauRepository.deleteByTrackingId(trackingId);
    }
}


