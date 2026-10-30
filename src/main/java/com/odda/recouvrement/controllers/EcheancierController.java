package com.odda.recouvrement.controllers;

import com.odda.recouvrement.dto.responses.EcheancierResponse;
import com.odda.recouvrement.entities.Apprenant;
import com.odda.recouvrement.repositories.ApprenantRepository;
import com.odda.recouvrement.services.EcheancierService;
import com.odda.recouvrement.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;

@RestController
@RequestMapping(API_BASE_URL + "echeanciers")
@RequiredArgsConstructor
public class EcheancierController {

    private final EcheancierService echeancierService;
    private final ApprenantRepository apprenantRepository; 

    
    @PostMapping("/generer/{apprenantTrackingId}")
    public ResponseEntity<BaseResponse<Void>> genererManuellement(@PathVariable UUID apprenantTrackingId) {
        // Récupérer l'apprenant actif
        Apprenant apprenant = apprenantRepository.findByTrackingIdAndActifTrue(apprenantTrackingId)
                .orElseThrow(() -> new IllegalArgumentException("Apprenant introuvable ou inactif."));

        // Lancer la génération
        echeancierService.genererPourApprenant(apprenant);

        // Retourner le succès
        return ResponseEntity.status(201).body(BaseResponse.<Void>builder()
                .statusCode(201)
                .description("Échéancier généré avec succès. Vérifiez la table 'echeanciers'.")
                .build());
    }

    
    @GetMapping("/apprenant/{trackingId}")
    public ResponseEntity<BaseResponse<List<EcheancierResponse>>> getByApprenant(@PathVariable UUID trackingId) {
        return ResponseEntity.ok(BaseResponse.<List<EcheancierResponse>>builder()
                .data(echeancierService.getEcheanciersByApprenant(trackingId))
                .statusCode(200)
                .description("Liste des échéances récupérée.")
                .build());
    }
}