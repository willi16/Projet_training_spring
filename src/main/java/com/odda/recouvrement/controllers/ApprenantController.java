package com.odda.recouvrement.controllers;

import com.odda.recouvrement.dto.requests.ApprenantRequest;
import com.odda.recouvrement.dto.responses.ApprenantResponse;
import com.odda.recouvrement.services.ApprenantService;
import com.odda.recouvrement.utils.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.odda.recouvrement.utils.AppConstant.API_BASE_URL;

@RestController
@RequestMapping(API_BASE_URL + "apprenants")
@RequiredArgsConstructor
public class ApprenantController {

    private final ApprenantService apprenantService;

    @PostMapping("/inscrire")
    public ResponseEntity<BaseResponse<ApprenantResponse>> inscrire(@Valid @RequestBody ApprenantRequest apprenantRequest) {
        ApprenantResponse response = apprenantService.save(apprenantRequest);

        return ResponseEntity.status(201).body(
            BaseResponse.<ApprenantResponse>builder()
                .data(response)
                .statusCode(201)
                .description("Apprenant inscrit avec succès.")
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ApprenantResponse>>> getAll() {
        List<ApprenantResponse> responses = apprenantService.getAll();

        return ResponseEntity.ok(
            BaseResponse.<List<ApprenantResponse>>builder()
                .data(responses)
                .statusCode(200)
                .description("Liste des apprenants récupérée.")
                .build()
        );
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<BaseResponse<ApprenantResponse>> getOne(@PathVariable UUID trackingId) {
        ApprenantResponse response = apprenantService.getOne(trackingId);

        return ResponseEntity.ok(
            BaseResponse.<ApprenantResponse>builder()
                .data(response)
                .statusCode(200)
                .description("Apprenant récupéré.")
                .build()
        );
    }

    
    @PutMapping("/{trackingId}")
    public ResponseEntity<BaseResponse<ApprenantResponse>> update(@PathVariable UUID trackingId,@Valid @RequestBody ApprenantRequest apprenantRequest) {
        ApprenantResponse response = apprenantService.update(trackingId, apprenantRequest);

        return ResponseEntity.ok(
            BaseResponse.<ApprenantResponse>builder()
                .data(response)
                .statusCode(200)
                .description("Apprenant mis à jour avec succès.")
                .build()
        );
    }

    @DeleteMapping("/{trackingId}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable UUID trackingId) {
        apprenantService.delete(trackingId);

        return ResponseEntity.ok(
            BaseResponse.<Void>builder()
                .statusCode(200)
                .description("Apprenant supprimé avec succès.")
                .build()
        );
    }
}