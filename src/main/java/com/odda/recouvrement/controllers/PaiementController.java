package com.odda.recouvrement.controllers;

import com.odda.recouvrement.dto.requests.PaiementRequest;
import com.odda.recouvrement.dto.responses.PaiementResponse;
import com.odda.recouvrement.services.PaiementService;
import com.odda.recouvrement.utils.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;

@RestController
@RequestMapping(API_BASE_URL + "paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping("/enregistrer")
    public ResponseEntity<BaseResponse<PaiementResponse>> enregistrer(@Valid @RequestBody PaiementRequest req) {
        PaiementResponse res = paiementService.enregistrerPaiement(req);
        return ResponseEntity.status(201).body(BaseResponse.<PaiementResponse>builder()
                .data(res)
                .statusCode(201)
                .description("Paiement enregistré et imputé avec succès.")
                .build());
    }

    @GetMapping("/historique/{apprenantTrackingId}")
    public ResponseEntity<BaseResponse<List<PaiementResponse>>> getHistorique(@PathVariable UUID apprenantTrackingId) {
        return ResponseEntity.ok(BaseResponse.<List<PaiementResponse>>builder()
                .data(paiementService.getHistorique(apprenantTrackingId))
                .statusCode(200)
                .description("Historique des paiements récupéré.")
                .build());
    }
}