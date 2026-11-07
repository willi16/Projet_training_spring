package com.odda.recouvrement.mappers;

import com.odda.recouvrement.dto.requests.PersonnelRequest;
import com.odda.recouvrement.dto.responses.PersonnelResponse;
import com.odda.recouvrement.entities.Personnel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PersonnelMapper {
    private final PasswordEncoder passwordEncoder;

    public PersonnelMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Personnel toEntity(PersonnelRequest personnelReq) {
        return Personnel.builder()
                .username(personnelReq.username())
                .password(passwordEncoder.encode(personnelReq.password())) 
                .nom(personnelReq.nom())
                .prenom(personnelReq.prenom())
                .email(personnelReq.email())
                .matricule(personnelReq.matricule())
                .role(personnelReq.role())
                .actif(true)
                .build();
    }

    public PersonnelResponse toResponse(Personnel personnel) {
        return new PersonnelResponse(
                personnel.getTrackingId(),
                personnel.getUsername(),
                personnel.getNom(),
                personnel.getPrenom(),
                personnel.getEmail(),
                personnel.getMatricule(),
                personnel.getRole(),
                personnel.isActif()
        );
    }

    public void updateEntity(PersonnelRequest personnelReq, Personnel personnel) {
        personnel.setUsername(personnelReq.username());
        personnel.setNom(personnelReq.nom());
        personnel.setPrenom(personnelReq.prenom());
        personnel.setEmail(personnelReq.email());
        personnel.setMatricule(personnelReq.matricule());
        personnel.setRole(personnelReq.role());
        if (personnelReq.password() != null && !personnelReq.password().isBlank()) {
            personnel.setPassword(passwordEncoder.encode(personnelReq.password()));
        }
    }
}