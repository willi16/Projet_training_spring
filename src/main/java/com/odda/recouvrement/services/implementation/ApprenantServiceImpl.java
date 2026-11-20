package com.odda.recouvrement.services.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.odda.recouvrement.dto.requests.ApprenantRequest;
import com.odda.recouvrement.dto.requests.ParentRequest;
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
import com.odda.recouvrement.services.EcheancierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprenantServiceImpl implements ApprenantService {

    private final ApprenantRepository apprenantRepository;
    private final ParentRepository parentRepository;
    private final ClasseRepository classeRepository;
    private final ApprenantMapper apprenantMapper;
    private final EcheancierService echeancierService;

    @Override
    @Transactional
    public ApprenantResponse save(ApprenantRequest apprenantRequest) {
        // 1. Résoudre le parent (création ou récupération)
        Parent parent = resolveParent(apprenantRequest.parent());

        // 2. Vérifier la classe
        Classe classe = classeRepository.findByTrackingId(apprenantRequest.classeTrackingId())
                .orElseThrow(() -> new IllegalArgumentException("Classe introuvable"));

        // 3. Mapper l'entité
        Apprenant apprenant = apprenantMapper.toApprenantEntity(apprenantRequest, parent, classe);
        
        // 4. GÉNÉRATION DU MATRICULE (La magie)
        apprenant.setMatricule(genererMatricule());
        apprenant.setActif(true);
        apprenant.setStatutPaiement(StatutPaiementEleve.A_JOUR);

        // 5. Sauvegarder et générer les échéances
        Apprenant saved = apprenantRepository.save(apprenant);
        echeancierService.genererPourApprenant(saved);

        return apprenantMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ApprenantResponse update(UUID trackingId, ApprenantRequest apprenantRequest) {
        // 1. Récupérer l'apprenant existant
        Apprenant existing = apprenantRepository.findByTrackingIdAndActifTrue(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Apprenant introuvable"));

        // 2. Résoudre le parent (mise à jour ou création)
        Parent parent = resolveParent(apprenantRequest.parent());

        // 3. Vérifier la classe
        Classe classe = classeRepository.findByTrackingId(apprenantRequest.classeTrackingId())
                .orElseThrow(() -> new IllegalArgumentException("Classe introuvable"));

        // 4. Déléguer la mise à jour au Mapper (Propre, comme votre exemple Classe !)
        apprenantMapper.updateEntity(apprenantRequest, existing, parent, classe);

        // 5. Sauvegarder et retourner
        return apprenantMapper.toResponse(apprenantRepository.save(existing));
    }

    //  MÉTHODE PRIVÉE POUR ÉLIMINER LES AVERTISSEMENTS JAUNES ET LA DUPLICATION
    private Parent resolveParent(ParentRequest parentPequest) {
        return parentRepository.findByContact(parentPequest.contact())
                .map(existingParent -> {
                    existingParent.setNom(parentPequest.nom());
                    existingParent.setPrenom(parentPequest.prenom());
                    existingParent.setEmail(parentPequest.email());
                    existingParent.setAdresse(parentPequest.adresse());
                    existingParent.setProfession(parentPequest.profession());
                    existingParent.setLienApprenant(parentPequest.lien());
                    return parentRepository.save(existingParent);
                })
                .orElseGet(() -> parentRepository.save(apprenantMapper.toParentEntity(parentPequest)));
    }

    private String genererMatricule() {
        long total = apprenantRepository.count();
        return String.format("APP-%d-%04d", LocalDate.now().getYear(), total + 1);
    }

    @Override
    @Transactional(readOnly = true)
    public ApprenantResponse getOne(UUID trackingId) {
        Apprenant apprenant = apprenantRepository.findByTrackingIdAndActifTrue(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Apprenant introuvable"));
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
    public void delete(UUID trackingId) {
        Apprenant apprenant = apprenantRepository.findByTrackingIdAndActifTrue(trackingId)
                .orElseThrow(() -> new IllegalArgumentException("Apprenant introuvable ou déjà supprimé"));
        apprenant.setActif(false);
        apprenantRepository.save(apprenant);
    }
}