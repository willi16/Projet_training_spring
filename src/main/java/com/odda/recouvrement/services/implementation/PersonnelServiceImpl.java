package com.odda.recouvrement.services.implementation;

import com.odda.recouvrement.dto.requests.PersonnelRequest;
import com.odda.recouvrement.dto.responses.PersonnelResponse;
import com.odda.recouvrement.entities.Personnel;
import com.odda.recouvrement.mappers.PersonnelMapper;
import com.odda.recouvrement.repositories.PersonnelRepository;
import com.odda.recouvrement.services.PersonnelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonnelServiceImpl implements PersonnelService {
    private final PersonnelRepository personnelRepo;
    private final PersonnelMapper personnelMapper;

    @Override
    @Transactional
    public PersonnelResponse save(PersonnelRequest personnelReq) {
        if (personnelRepo.existsByUsernameOrEmail(personnelReq.username(), personnelReq.email())) {
            throw new IllegalArgumentException("Ce nom d'utilisateur ou cet email est déjà utilisé.");
        }
        if (personnelReq.matricule() != null && personnelRepo.existsByMatricule(personnelReq.matricule())) {
            throw new IllegalArgumentException("Ce matricule est déjà utilisé.");
        }
        return personnelMapper.toResponse(personnelRepo.save(personnelMapper.toEntity(personnelReq)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonnelResponse> getAll() {
        return personnelRepo.findAll()
                .stream()
                .map(personnelMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PersonnelResponse getOne(UUID trackingId) {
        return personnelRepo.findByTrackingId(trackingId)
                .map(personnelMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Personnel introuvable."));
    }

    @Override
    @Transactional
    public PersonnelResponse update(UUID trackingId, PersonnelRequest personnelReq) {
        Personnel personnel = personnelRepo.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Personnel introuvable."));
        personnelMapper.updateEntity(personnelReq, personnel);
        return personnelMapper.toResponse(personnelRepo.save(personnel));
    }

    @Override
    @Transactional
    public void delete(UUID trackingId) {
        Personnel personnel = personnelRepo.findByTrackingId(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Personnel introuvable."));
        personnel.setActif(false); 
        personnelRepo.save(personnel);
    }
}