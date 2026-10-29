package com.odda.recouvrement.services.implementation;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.odda.recouvrement.dto.requests.ApprenantRequest;
import com.odda.recouvrement.dto.responses.ApprenantResponse;
import com.odda.recouvrement.entities.Apprenant;
import com.odda.recouvrement.entities.Classe;
import com.odda.recouvrement.entities.Parent;
import com.odda.recouvrement.enums.StatutPaiementEleve;
import com.odda.recouvrement.mappers.ApprenantMapper;
import com.odda.recouvrement.repositories.ApprenantRepository;
import com.odda.recouvrement.repositories.ClasseRepository;
import com.odda.recouvrement.repositories.ParentRepository;
import com.odda.recouvrement.services.ApprenantService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprenantServiceImpl implements ApprenantService {

    private final ApprenantRepository apprenantRepository;
    private final ParentRepository parentRepository;
    private final ClasseRepository classeRepository;
    private final ApprenantMapper apprenantMapper;

    @Override
    @Transactional
    public ApprenantResponse save(ApprenantRequest apprenantRequest) {
        // Vérification unicité matricule
        if (apprenantRepository.existsByMatricule(apprenantRequest.matricule())) {
            throw new IllegalArgumentException(
                    "Un apprenant avec le matricule [" + apprenantRequest.matricule() + "] existe déjà.");
        }

        // Gestion du Parent (Recherche par contact)
        Parent parent = parentRepository.findByContact(apprenantRequest.parent().contact())
                .orElseGet(() -> {
                    Parent nouveauParent = apprenantMapper.toParentEntity(apprenantRequest.parent());
                    return parentRepository.save(nouveauParent);
                });

        // Vérification de la classe
        Classe classe = classeRepository.findById(apprenantRequest.classeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Classe introuvable avec l'ID : " + apprenantRequest.classeId()));

        Apprenant apprenant = apprenantMapper.toApprenantEntity(apprenantRequest, parent, classe);
        apprenant.setActif(true);
        apprenant.setStatutPaiement(StatutPaiementEleve.A_JOUR);

        Apprenant saved = apprenantRepository.save(apprenant);

        // TODO: Appel futur à echeancierGeneratorService.generer(saved);

        return apprenantMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApprenantResponse getOne(UUID trackingId) {
        Apprenant apprenant = apprenantRepository.findByTrackingIdAndActifTrue(trackingId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Apprenant introuvable avec le trackingId : " + trackingId));

        return apprenantMapper.toResponse(apprenant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApprenantResponse> getAll() {

        return apprenantRepository.findAllByActifTrue().stream()
                .map(apprenantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ApprenantResponse update(UUID trackingId, ApprenantRequest apprenantRequest) {
        // Récupérer l'apprenant existant
        Apprenant existing = apprenantRepository.findByTrackingIdAndActifTrue(trackingId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Apprenant introuvable avec le trackingId : " + trackingId));

        Parent parent = parentRepository.findByContact(apprenantRequest.parent().contact())
                .map(existingParent -> {
                    existingParent.setNom(apprenantRequest.parent().nom());
                    existingParent.setPrenom(apprenantRequest.parent().prenom());
                    existingParent.setEmail(apprenantRequest.parent().email());
                    existingParent.setAdresse(apprenantRequest.parent().adresse());
                    existingParent.setProfession(apprenantRequest.parent().profession());
                    existingParent.setLienApprenant(apprenantRequest.parent().lien());
                    return parentRepository.save(existingParent);
                })
                .orElseGet(() -> {
                    // Si le contact n'existe pas, on en crée un nouveau
                    Parent nouveauParent = apprenantMapper.toParentEntity(apprenantRequest.parent());
                    return parentRepository.save(nouveauParent);
                });

        // Vérification de la classe
        if (apprenantRequest.classeId() != null) {
            Classe nouvelleClasse = classeRepository.findById(apprenantRequest.classeId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Classe introuvable avec l'ID : " + apprenantRequest.classeId()));
            existing.setClasse(nouvelleClasse);
        }

        apprenantMapper.toEntityUpdate(apprenantRequest, existing);

        Apprenant updated = apprenantRepository.save(existing);

        return apprenantMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(UUID trackingId) {

        Apprenant apprenant = apprenantRepository.findByTrackingIdAndActifTrue(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Apprenant introuvable ou déjà supprimé."));

        apprenant.setActif(false);
        apprenantRepository.save(apprenant);
    }
}
