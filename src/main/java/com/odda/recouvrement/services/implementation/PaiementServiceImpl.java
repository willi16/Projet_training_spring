package com.odda.recouvrement.services.implementation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.odda.recouvrement.dto.requests.PaiementRequest;
import com.odda.recouvrement.dto.responses.PaiementResponse;
import com.odda.recouvrement.entities.Apprenant;
import com.odda.recouvrement.entities.Echeancier;
import com.odda.recouvrement.entities.Paiement;
import com.odda.recouvrement.entities.Personnel;
import com.odda.recouvrement.enums.StatutEcheance;
import com.odda.recouvrement.mappers.PaiementMapper;
import com.odda.recouvrement.repositories.ApprenantRepository;
import com.odda.recouvrement.repositories.EcheancierRepository;
import com.odda.recouvrement.repositories.PaiementRepository;
import com.odda.recouvrement.repositories.PersonnelRepository;
import com.odda.recouvrement.services.PaiementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final ApprenantRepository apprenantRepository;
    private final EcheancierRepository echeancierRepository;
    private final PersonnelRepository personnelRepository;
    private final PaiementMapper paiementMapper;

    @Override
    @Transactional 
    public PaiementResponse enregistrerPaiement(PaiementRequest paiementReq) {

        // Récupérer l'apprenant
        Apprenant apprenant = apprenantRepository.findByTrackingIdAndActifTrue(paiementReq.apprenantTrackingId())
                .orElseThrow(() -> new IllegalArgumentException("Apprenant introuvable ou inactif."));

        // Récupérer le caissier
        Personnel caissier = null;
        if (paiementReq.caissierTrackingId() != null) {
            caissier = personnelRepository.findByTrackingId(paiementReq.caissierTrackingId())
                    .orElseThrow(() -> new IllegalArgumentException("Caissier introuvable."));
        }


        String numeroRecu = "REC-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + 
                            "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();


        // Récupérer les échéances NON PAYÉES, triées par date (FIFO)
        List<Echeancier> echeanciers = echeancierRepository.findByApprenantTrackingId(paiementReq.apprenantTrackingId())
                .stream()
                .filter(e -> e.getStatut() != StatutEcheance.PAYE && e.getStatut() != StatutEcheance.ANNULE)
                .sorted((e1, e2) -> e1.getDateEcheance().compareTo(e2.getDateEcheance()))
                .toList();

        BigDecimal montantRestantAImputer = paiementReq.montantTotal();
        BigDecimal totalImpute = BigDecimal.ZERO;

        // Algorithme FIFO d'imputation
        for (Echeancier echeance : echeanciers) {
            if (montantRestantAImputer.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal resteDû = echeance.getMontantPrevu().subtract(echeance.getMontantPaye());
            BigDecimal montantAffecte = resteDû.min(montantRestantAImputer);

            echeance.setMontantPaye(echeance.getMontantPaye().add(montantAffecte));
            montantRestantAImputer = montantRestantAImputer.subtract(montantAffecte);
            totalImpute = totalImpute.add(montantAffecte);

            // Mise à jour du statut de l'échéance
            if (echeance.getMontantPaye().compareTo(echeance.getMontantPrevu()) >= 0) {
                echeance.setStatut(StatutEcheance.PAYE);
            } else {
                echeance.setStatut(StatutEcheance.PARTIELLEMENT_PAYE);
            }
        }

        // Sauvegarder les échéances modifiées
        echeancierRepository.saveAll(echeanciers);

        // Enregistrer le paiement
        Paiement paiement = paiementRepository.save(paiementMapper.toEntity(paiementReq, apprenant, caissier));

        // Calcul du reliquat
        BigDecimal reliquat = paiementReq.montantTotal().subtract(totalImpute);
        

        return paiementMapper.toResponse(paiement, totalImpute, reliquat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementResponse> getHistorique(UUID apprenantTrackingId) {
        return paiementRepository.findByApprenant_TrackingIdOrderByDatePaiementDesc(apprenantTrackingId)
                .stream()
                .map(p -> paiementMapper.toResponse(p, BigDecimal.ZERO, BigDecimal.ZERO))
                .toList();
    }

    
}
