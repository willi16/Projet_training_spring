package com.odda.recouvrement.services.implementation;


import com.odda.recouvrement.dto.requests.ClasseRequest;
import com.odda.recouvrement.dto.responses.ClasseResponse;
import com.odda.recouvrement.entities.Classe;
import com.odda.recouvrement.entities.Niveau;
import com.odda.recouvrement.mappers.ClasseMapper;
import com.odda.recouvrement.repositories.ClasseRepository;
import com.odda.recouvrement.repositories.NiveauRepository;
import com.odda.recouvrement.services.ClasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;
    private final NiveauRepository niveauRepository;
    private final ClasseMapper classeMapper;

    @Override
    @Transactional
    public ClasseResponse save(ClasseRequest classeReq) {
        Niveau niveau = niveauRepository.findById(classeReq.niveauId())
                .orElseThrow(() -> new IllegalArgumentException("Niveau introuvable avec l'ID : " + classeReq.niveauId()));
        
        Classe saved = classeRepository.save(classeMapper.toEntity(classeReq, niveau));
        return classeMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClasseResponse> getAll() {
        return classeRepository.findAll().stream().map(classeMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClasseResponse getOne(UUID trackingId) {
        Classe classe = classeRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Classe introuvable avec l'ID : " + trackingId));
        return classeMapper.toResponse(classe);
    }

    @Override
    @Transactional
    public ClasseResponse update(UUID trackingId, ClasseRequest classeReq) {
        Classe classe = classeRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Classe introuvable avec l'ID : " + trackingId));
        
        Niveau niveau = niveauRepository.findById(classeReq.niveauId())
                .orElseThrow(() -> new IllegalArgumentException("Niveau introuvable"));

        classeMapper.updateEntity(classeReq, classe, niveau);
        return classeMapper.toResponse(classeRepository.save(classe));
    }

    @Override
    @Transactional
    public void delete(UUID trackingId) {
        classeRepository.deleteByTrackingId(trackingId);
    }
}

