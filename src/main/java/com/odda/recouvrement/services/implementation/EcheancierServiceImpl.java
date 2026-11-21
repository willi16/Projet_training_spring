package com.odda.recouvrement.services.implementation;

import com.odda.recouvrement.dto.responses.EcheancierResponse;
import com.odda.recouvrement.dto.responses.RetardResponse;
import com.odda.recouvrement.entities.Apprenant;
import com.odda.recouvrement.entities.Echeancier;
import com.odda.recouvrement.entities.Parent;
import com.odda.recouvrement.entities.TypeFrais;
import com.odda.recouvrement.enums.StatutEcheance;
import com.odda.recouvrement.mappers.EcheancierMapper;
import com.odda.recouvrement.repositories.EcheancierRepository;
import com.odda.recouvrement.repositories.TypeFraisRepository;
import com.odda.recouvrement.services.EcheancierService;
import com.odda.recouvrement.utils.TrancheRule;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EcheancierServiceImpl implements EcheancierService {

    private final TypeFraisRepository typeRepository;
    private final EcheancierRepository echeancierRepository;
    private final EcheancierMapper echeanceMapper;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void genererPourApprenant(Apprenant apprenant) {

        // Récupérer les frais applicables
        List<TypeFrais> typesFrais = typeRepository.findApplicableFrais(apprenant.getClasse().getId());

        if (typesFrais.isEmpty()) {

            return;
        }

        // Date de référence (Date de rentrée ou date du jour)
        LocalDate dateReference = LocalDate.now().plusMonths(1);
        List<Echeancier> list = new ArrayList<>();

        for (TypeFrais type : typesFrais) {

            try {

                TrancheRule config = objectMapper.readValue(type.getReglesDecoupage(), TrancheRule.class);

                // Vérification 100%
                int totalPct = 0;
                for (TrancheRule.Tranche t : config.tranches()) {

                    totalPct += t.pourcentage();
                }
                if (totalPct != 100) {

                    throw new RuntimeException("Erreur config frais " + type.getCode());
                }

                // Création des tranches
                for (TrancheRule.Tranche regle : config.tranches()) {

                    BigDecimal montant = type.getMontantReference()
                            .multiply(BigDecimal.valueOf(regle.pourcentage()))
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                    list.add(Echeancier.builder()
                            .apprenant(apprenant)
                            .typeFrais(type)
                            .montantPrevu(montant)
                            .montantPaye(BigDecimal.ZERO)
                            .dateEcheance(dateReference.plusDays(regle.delaiJours()))
                            .statut(StatutEcheance.A_VENIR)
                            .build());
                }
            } catch (Exception e) {

                throw new RuntimeException("Erreur génération", e);
            }
        }
        echeancierRepository.saveAll(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EcheancierResponse> getEcheanciersByApprenant(UUID apprenantTrackingId) {
        List<Echeancier> list = echeancierRepository.findByApprenantTrackingId(apprenantTrackingId);
        return list.stream().map(echeanceMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RetardResponse> getAllRetards() {
        LocalDate aujourdHui = LocalDate.now();

        return echeancierRepository.findAllEnRetard().stream()
                .map(e -> {
                    long joursRetard = ChronoUnit.DAYS.between(e.getDateEcheance(), aujourdHui);
                    Apprenant a = e.getApprenant();
                    Parent p = a.getParent();

                    return new RetardResponse(
                            e.getTrackingId(),
                            a.getTrackingId(),
                            a.getMatricule(),
                            a.getNom() + " " + a.getPrenom(),
                            a.getClasse() != null ? a.getClasse().getLibelle() : null,
                            e.getTypeFrais().getLibelle(),
                            e.getDateEcheance(),
                            e.getMontantPrevu(),
                            e.getMontantPaye(),
                            e.getMontantPrevu().subtract(e.getMontantPaye()),
                            joursRetard,
                            e.getStatut().name(),
                            p != null ? p.getNom() + " " + p.getPrenom() : null,
                            p != null ? p.getContact() : null);
                })
                .collect(Collectors.toList());
    }
}